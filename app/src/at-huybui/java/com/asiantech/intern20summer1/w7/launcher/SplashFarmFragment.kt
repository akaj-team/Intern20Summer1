package com.asiantech.intern20summer1.w7.launcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
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
        private const val JSON_FILE_NAME = "plants.json"
        internal fun newInstance() = SplashFarmFragment()
    }

    private var dataBase: ConnectDataBase? = null
    private var stepProgressBar = 1
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
        val user = dataBase?.userDao()?.getUser()
        var plants = dataBase?.plantDao()?.getAllPlant()
        progressBarFarm?.progress = 0
        object : CountDownTimer(
            SPLASH_TIMER,
            PROGRESS_TIMER_STEP
        ) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm?.progress = progressBarFarm.progress + stepProgressBar

                when (progressBarFarm?.progress) {
                    10 -> {
                        if (plants?.size == 0) {
                            showToast("Giải nén dữ liệu")
                            isLoadDataUrl = true
                            saveDataFromJsonFile(requireContext())
                        } else {
                            stepProgressBar = 5
                        }
                    }
                    20 -> {
                        plants = dataBase?.plantDao()?.getAllPlant()
                        d("XXXX", "get all plant")
                    }
                    30 -> {
                        if (isLoadDataUrl) {
                            if (plants?.size == 0) {
                                progressBarFarm?.progress = 0
                            } else {
                                plants?.forEach { plant ->
                                    plant.plantId?.let {
                                        DownLoadImage(requireContext(), it).execute(
                                            plant.imageUrl
                                        )
                                    }
                                }
                            }
                        }
                    }

                    PROGRESS_MAX_VALUE -> {
                        val part = requireContext().getDir("imagePlants", Context.MODE_PRIVATE)
                        plants?.forEach { plant ->
                            plant.plantId?.let {
                                dataBase?.plantDao()?.editUri("$part/${it}.jpg", it)
                            }
                        }
                        if (user == null) {
                            (activity as LauncherFarmActivity).handleReplaceFragment(
                                RegisterFarmFragment.newInstance()
                            )
                        } else {
                            val intent = Intent(context, MainFarmActivity::class.java)
                            startActivity(intent)
                            (activity as LauncherFarmActivity).finish()
                        }
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

    private fun initUriForImage() {

    }

    fun showToast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text.toString(), duration).show()
    }
}
