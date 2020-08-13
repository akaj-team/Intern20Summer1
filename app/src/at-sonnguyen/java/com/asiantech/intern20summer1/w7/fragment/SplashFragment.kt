package com.asiantech.intern20summer1.w7.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.activity.HomeActivity
import com.asiantech.intern20summer1.w7.activity.SplashActivity
import com.asiantech.intern20summer1.w7.database.PlantDatabase
import com.asiantech.intern20summer1.w7.database.data.DownloadPlantImage
import com.asiantech.intern20summer1.w7.database.data.DownloadPlantImage.Companion.FILE_TAIL
import com.asiantech.intern20summer1.w7.database.data.DownloadPlantImage.Companion.NAME_DIR
import com.asiantech.intern20summer1.w7.database.data.Plant
import com.asiantech.intern20summer1.w7.extension.replaceFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_splash.*
import java.util.concurrent.Executors

class SplashFragment : Fragment() {

    private var database: PlantDatabase? = null
    private var isLoadDataUrl = false

    companion object {
        private const val MILLIS_IN_FEATURE = 50000L
        private const val COUNT_DOWN_INTERVAL = 100L
        private const val PROGRESS_MAX_VALUE = 100
        private const val PROGRESS_FAST_STEP = 5L
        private const val JSON_FILE_NAME = "plants.json"
        private const val POINT_CHECK_DATABASE = 5
        private const val POINT_LOADING_DATABASE = 10
        private const val POINT_LOADING_DATA_URL = 20
        internal fun newInstance() = SplashFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = PlantDatabase.getInstance(requireContext())
        handleProgressBar()

    }

    private fun handleProgressBar() {
        var plants = database?.plantDao()?.getAllPlant()
        progressBarSplash?.progress = 0
        object : CountDownTimer(MILLIS_IN_FEATURE, COUNT_DOWN_INTERVAL) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                progressBarSplash?.progress = progressBarSplash.progress + 1
                when (progressBarSplash?.progress) {
                    POINT_CHECK_DATABASE -> {
                        if (plants?.size == 0) {
                            isLoadDataUrl = true
                            provideDatabase(requireContext())
                        }
                    }
                    POINT_LOADING_DATABASE -> {
                        plants = database?.plantDao()?.getAllPlant()
                    }
                    POINT_LOADING_DATA_URL -> {
                        downloadPlantImage(plants)
                    }
                    PROGRESS_MAX_VALUE -> {
                        getImageUri(plants)
                        checkAccount()
                    }
                }
            }

        }
    }

    //    companion object {
//        private const val MILLIS_IN_FEATURE = 10000L
//        private const val COUNT_DOWN_INTERVAL = 100L
//        fun newInstance() = SplashFragment()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.w7_fragment_splash, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initListener()
//    }
//
//    private fun initListener() {
//        imgSplash.setOnClickListener {
//            progressBarSplash.progress += 1
//        }
//        timer.start()
//    }
//

//    }
//
//    private val timer = object : CountDownTimer(MILLIS_IN_FEATURE, COUNT_DOWN_INTERVAL) {
//        override fun onTick(millisUntilFinished: Long) {
//            progressBarSplash.progress += 1
//        }
//
//        override fun onFinish() {
//            if (checkAccount()){
//                val intent = Intent(activity,HomeActivity::class.java)
//                startActivity(intent)
//                activity?.finish()
//            } else {
//            }
//        }
//    }
//    private fun checkAccount() : Boolean{
//        val sharedRef = activity?.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
//        if (sharedRef?.getString(SHARED_PREFERENCE_USER_NAME_KEY, null) != null) {
//            return true
//        }
//        return false
//    }
    private fun provideDatabase(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open(JSON_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    database?.plantDao()?.insertPlants(plants)
                }
            }
        }
    }

    private fun downloadPlantImage(plants: List<Plant>?) {
        if (isLoadDataUrl) {
            if (plants?.size == 0) {
                progressBarSplash?.progress = 0
            } else {
                plants?.forEach { plant ->
                    plant.plantId?.let {
                        DownloadPlantImage(requireContext(), it).execute(plant.imageUrl)
                    }
                }
            }
        }
    }
    private fun getImageUri(plants: List<Plant>?) {
        val part = requireContext().getDir(NAME_DIR, Context.MODE_PRIVATE)
        plants?.forEach { plant ->
            plant.plantId?.let {
                database?.plantDao()?.editImageUri("$part/${it}${FILE_TAIL}", it)
            }
        }
    }
    fun checkAccount(){
        val user = database?.userDao()?.getAllUser()
        if (user == null) {
            (activity as SplashActivity).replaceFragment(R.id.frameLayoutMain,RegisterFragment.newInstance())
        } else {
            val intent = Intent(context,HomeActivity::class.java)
            startActivity(intent)
            (activity as SplashActivity)?.finish()
        }
    }
}
