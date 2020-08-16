package com.asiantech.intern20summer1.w7.launcher.fragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.companion.App
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.launcher.activity.LauncherFarmActivity
import com.asiantech.intern20summer1.w7.main.activity.MainFarmActivity
import com.asiantech.intern20summer1.w7.model.PlantModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.`at-huybui`.fragment_splash_farm.*
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors


/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for splash fragments
 */


class SplashFarmFragment : Fragment() {

    companion object {
        private const val SPLASH_TIMER = 50000L
        private const val PROGRESS_LOW_STEP = 100L
        private const val PROGRESS_MAX_VALUE = 100
        private const val PROGRESS_FAST_STEP = 2L
        private const val JSON_FILE_NAME = "plants.json"
        private const val POINT_LOADING_DATA_URL = 10
        private const val SIZE_IMAGE = 500
        private const val QUALITY_IMAGE = 100
        private const val KEY_DATA = "key_data"
        private const val KEY_JSON = "key_json"
        internal fun newInstance() = SplashFarmFragment()
    }

    private var dataBase: ConnectDataBase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_farm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBase = ConnectDataBase.dataBaseConnect(requireContext())
    }

    override fun onStart() {
        super.onStart()
        handleForProgressBar()
    }

    private fun handleForProgressBar() {
        progressBarFarm?.progress = 0
        val pref = requireContext().getSharedPreferences(App.NAME_PREFERENCE, MODE_PRIVATE)
        val isData = pref.getBoolean(KEY_DATA, false)
        if (isData) {
            loadingFastProgressBar()
        } else {
            loadingLowProgressBar()
        }
    }

    private fun loadingFastProgressBar() {
        object : CountDownTimer(
            SPLASH_TIMER,
            PROGRESS_FAST_STEP
        ) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm?.progress = progressBarFarm.progress + 1
                if (progressBarFarm?.progress == PROGRESS_MAX_VALUE) {
                    intentActivity()
                    this.cancel()
                }
            }
        }.start()
    }

    private fun loadingLowProgressBar() {
        val pref = requireContext().getSharedPreferences(App.NAME_PREFERENCE, MODE_PRIVATE)
        val isJsonStatus = pref.getBoolean(KEY_JSON, false)
        if (!isJsonStatus) {
            saveDataFromJsonFile(requireContext())
        }
        object : CountDownTimer(
            SPLASH_TIMER,
            PROGRESS_LOW_STEP
        ) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm?.progress = progressBarFarm.progress + 1
                when (progressBarFarm?.progress) {
                    POINT_LOADING_DATA_URL -> {
                        val plants = dataBase?.plantDao()?.getAllPlant()
                        handleInternet(this, plants)
                    }
                }
            }

            override fun onFinish() {
                (activity as MainFarmActivity).finish()
            }
        }.start()
    }

    private fun saveDataFromJsonFile(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open(JSON_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<PlantModel>>() {
                    }.type
                    val plants: List<PlantModel> = Gson().fromJson(jsonReader, plantType)
                    dataBase?.plantDao()?.insertPlants(plants)
                }
            }
            requireContext().getSharedPreferences(App.NAME_PREFERENCE, MODE_PRIVATE).edit()
                .putBoolean(KEY_JSON, true).apply()
        }
    }

    private fun intentActivity() {
        requireContext().getSharedPreferences(App.NAME_PREFERENCE, MODE_PRIVATE).edit()
            .putBoolean(KEY_DATA, true).apply()
        val user = dataBase?.userDao()?.getUser()
        if (user == null) {
            (activity as LauncherFarmActivity).handleReplaceFragment(
                RegisterFarmFragment.newInstance()
            )
        } else {
            val intent = Intent(context, MainFarmActivity::class.java)
            startActivity(intent)
            (activity as LauncherFarmActivity).finish()
        }
    }

    private fun handleInternet(timer: CountDownTimer, plants: List<PlantModel>?) {
        if (isCheckInternet()) {
            timer.cancel()
            downLoadImageFromUrl(plants)
            showToast(getString(R.string.w7_loading_data))
        } else {
            timer.cancel()
            selectInternetDialog()
            showToast(getString(R.string.w7_splash_internet_connect_request), Toast.LENGTH_LONG)
        }
    }

    private fun isCheckInternet(): Boolean {
        var returnValue = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        returnValue = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        returnValue = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        returnValue = true
                    }
                }
            }
        }
        return returnValue
    }

    private fun selectInternetDialog() {
        val builder = (activity as LauncherFarmActivity).let { AlertDialog.Builder(it) }
        builder.setTitle(getString(R.string.w7_splash_select_internet))
        val select = arrayOf(
            getString(R.string.w7_splash_wifi),
            getString(R.string.w7_splash_mobile_network)
        )
        builder.setItems(select) { _, which ->
            when (which) {
                0 -> {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
                1 -> {
                    startActivity(Intent(Settings.ACTION_DATA_ROAMING_SETTINGS))
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun downLoadImageFromUrl(plants: List<PlantModel>?) {
        progressBarFarm?.progress = 1
        Thread(Runnable {
            plants?.forEachIndexed { index, plant ->
                var directory: File? = null
                val requestOptions = RequestOptions().override(SIZE_IMAGE, SIZE_IMAGE)
                    .downsample(DownsampleStrategy.CENTER_INSIDE)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)

                val bitmap = Glide.with(requireContext()).asBitmap().load(plant.imageUrl)
                    .apply(requestOptions).submit().get()

                try {
                    directory = requireContext().getDir(App.NAME_DIR, MODE_PRIVATE)
                    val path = File(directory, "${plant.plantId}${App.FILE_TAIL}")
                    FileOutputStream(path).apply {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_IMAGE, this)
                        flush()
                        close()
                    }
                } catch (ae: ArithmeticException) {
                    ae.printStackTrace()
                } catch (ne: NumberFormatException) {
                    ne.printStackTrace()
                } catch (ie: IllegalArgumentException) {
                    ie.printStackTrace()
                }
                (activity as LauncherFarmActivity).runOnUiThread {
                    progressBarFarm?.progress =
                        progressBarFarm.progress + (PROGRESS_MAX_VALUE - POINT_LOADING_DATA_URL) / plants.size
                    val uriImage =
                        "${directory?.absolutePath.toString()}/${plant.plantId}${App.FILE_TAIL}"
                    dataBase?.plantDao()?.editUri(uriImage, plant.plantId)
                    if (index == plants.size - 1) {
                        progressBarFarm?.progress = PROGRESS_MAX_VALUE
                        intentActivity()
                    }
                }
            }
        }).start()
    }

    private fun showToast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text.toString(), duration).show()
    }
}
