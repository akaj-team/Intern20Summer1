package com.asiantech.intern20summer1.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.VegetableFarmMainActivity
import com.asiantech.intern20summer1.activity.VegetableSplashActivity
import com.asiantech.intern20summer1.data.DownLoadImage
import com.asiantech.intern20summer1.data.MyApp
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.`at-vuongphan`.w7_splash_fragment.*
import java.util.concurrent.Executors

class VegetableSplashFragment : Fragment() {
    private var dataBase: VegetableDB? = null
    private var isLoadDataUrl = false

    companion object {
        private const val SPLASH_TIMER = 50000L
        private const val PROGRESS_TIMER_STEP = 100L
        private const val PROGRESS_MAX_VALUE = 100
        private const val PROGRESS_FAST_STEP = 5L
        private const val JSON_FILE_NAME = "plants.json"
        private const val POINT_CHECK_DATABASE = 5
        private const val POINT_LOADING_DATA_URL = 20
        private const val POINT_LOADING_DATABASE = 10
        internal fun newInstance() = VegetableSplashFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBase = VegetableDB.dataBaseConnect(requireContext())
        handleForProgressBar()
    }

    private fun handleForProgressBar() {
        var plants = dataBase?.plantDao()?.getAllPlant()
        progressSplash?.progress = 0
        object : CountDownTimer(
            SPLASH_TIMER,
            PROGRESS_TIMER_STEP
        ) {
            override fun onTick(millisUntilFinished: Long) {
                progressSplash?.progress = progressSplash.progress + 1
                when (progressSplash?.progress) {
                    POINT_CHECK_DATABASE -> {
                        if (plants?.size == 0) {
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
                        initImageInternet(plants)
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

    private fun loadingFastProgressBar() {
        object : CountDownTimer(SPLASH_TIMER, PROGRESS_FAST_STEP) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                progressSplash?.progress = progressSplash.progress + 1
                when (progressSplash?.progress) {
                    PROGRESS_MAX_VALUE -> {
                        intentActivity()
                        this.cancel()
                    }
                }
            }
        }.start()
    }

    private fun intentActivity() {
        val user = dataBase?.userDao()?.getUser()
        if (user == null) {
            (activity as? VegetableSplashActivity)?.openFragment(
                VegetableRegisterFragment.newInstance()
            )
        } else {
            val intent = Intent(context, VegetableFarmMainActivity::class.java)
            startActivity(intent)
            (activity as VegetableSplashActivity).finish()
        }
    }

    private fun initImageInternet(plants: List<Plant>?) {
        if (isLoadDataUrl) {
            if (plants?.size == 0) {
                progressSplash?.progress = 0
            } else {
                plants?.forEach { plant ->
                    plant.plantId?.let {
                        DownLoadImage(requireContext(), it).execute(plant.imageUrl)
                    }
                }
            }
        }
    }

    private fun initUriForImage(plants: List<Plant>?) {
        val part = requireContext().getDir(MyApp.NAME_DIR, Context.MODE_PRIVATE)
        plants?.forEach { plant ->
            plant.plantId?.let {
                dataBase?.plantDao()?.editUri("$part/${it}${MyApp.FILE_TAIL}", it)
            }
        }
    }

    private fun saveDataFromJsonFile(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open(JSON_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    dataBase?.plantDao()?.insertPlants(plants)
                }
            }
        }
    }
}
