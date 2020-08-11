package com.asiantech.intern20summer1.w7.launcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.companion.App
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.launcher.asynctask.DownLoadImage
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.model.PlantModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.`at-huybui`.fragment_splash_farm.*
import java.util.concurrent.Executors

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for splash fragments
 */


class SplashFarmFragment : Fragment() {

    companion object {
        private const val SPLASH_TIMER = 50000L
        private const val PROGRESS_TIMER_STEP = 100L
        private const val PROGRESS_MAX_VALUE = 100
        private const val PROGRESS_FAST_STEP = 5L
        private const val JSON_FILE_NAME = "plants.json"
        private const val POINT_CHECK_DATABASE = 5
        private const val POINT_LOADING_DATABASE = 10
        private const val POINT_LOADING_DATA_URL = 20
        internal fun newInstance() = SplashFarmFragment()
    }

    private var dataBase: ConnectDataBase? = null
    private var isLoadDataUrl = false

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
        handleForProgressBar()
    }

    private fun handleForProgressBar() {
        var plants = dataBase?.plantDao()?.getAllPlant()
        progressBarFarm?.progress = 0
        object : CountDownTimer(
            SPLASH_TIMER,
            PROGRESS_TIMER_STEP
        ) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm?.progress = progressBarFarm.progress + 1
                when (progressBarFarm?.progress) {
                    POINT_CHECK_DATABASE -> {
                        if (plants?.size == 0) {
                            showToast(getString(R.string.w7_loading_data))
                            isLoadDataUrl = true
                            saveDataFromJsonFile(requireContext())
                        } else {
                            this.cancel()
                            loadingFastProgressBar()

                        }
                    }
                    POINT_LOADING_DATABASE -> {
                        plants = dataBase?.plantDao()?.getAllPlant()
                    }
                    POINT_LOADING_DATA_URL -> {
                        loadingDataInternet(plants)
                    }

                    PROGRESS_MAX_VALUE -> {
                        initUriForImage(plants)
                        intentActivity()
                        this.cancel()
                    }
                }
            }

            override fun onFinish() {}
        }.start()
    }

    private fun saveDataFromJsonFile(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open(JSON_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<PlantModel>>() {}.type
                    val plants: List<PlantModel> = Gson().fromJson(jsonReader, plantType)
                    dataBase?.plantDao()?.insertPlants(plants)
                }
            }
        }
    }

    private fun loadingDataInternet(plants: List<PlantModel>?) {
        if (isLoadDataUrl) {
            if (plants?.size == 0) {
                progressBarFarm?.progress = 0
            } else {
                plants?.forEach { plant ->
                    plant.plantId?.let {
                        DownLoadImage(requireContext(), it).execute(plant.imageUrl)
                    }
                }
            }
        }
    }

    private fun intentActivity() {
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

    private fun initUriForImage(plants: List<PlantModel>?) {
        val part = requireContext().getDir(App.NAME_DIR, Context.MODE_PRIVATE)
        plants?.forEach { plant ->
            plant.plantId?.let {
                dataBase?.plantDao()?.editUri("$part/${it}${App.FILE_TAIL}", it)
            }
        }
    }

    private fun loadingFastProgressBar() {
        object : CountDownTimer(SPLASH_TIMER, PROGRESS_FAST_STEP) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm?.progress = progressBarFarm.progress + 1
                when (progressBarFarm?.progress) {
                    PROGRESS_MAX_VALUE -> {
                        intentActivity()
                        this.cancel()
                    }
                }
            }
        }.start()
    }

    fun showToast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text.toString(), duration).show()
    }
}
