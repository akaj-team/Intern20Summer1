package com.asiantech.intern20summer1.w7.launcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.database.ConnectDataBase.Companion.providerDatabase
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
        private const val SPLASH_TIMER = 10000L
        private const val PROGRESS_TIMER_STEP = 20L
        private const val PROGRESS_MAX_VALUE = 100
        private const val JSON_FILE_NAME = "plants.json"
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
        handleForProgressBar()
    }

    private fun handleForProgressBar() {
        val user = dataBase?.userDao()?.getUser()
        val plants = dataBase?.plantDao()?.getAllPlant()
        progressBarFarm?.progress = 0
        object : CountDownTimer(
            SPLASH_TIMER,
            PROGRESS_TIMER_STEP
        ) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm?.progress = progressBarFarm.progress + 1

                if (progressBarFarm?.progress == 50) {
                    if (plants?.size == 0) {
                        d("XXXX", "data tree null, add data")
                        saveData(requireContext())
                    } else {
                        d("XXXX", plants.toString())
                    }
                }
                if (progressBarFarm?.progress == PROGRESS_MAX_VALUE) {
                    this.cancel()
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
            }

            override fun onFinish() {}
        }.start()
    }

    private fun saveData(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open(JSON_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<PlantModel>>() {}.type
                    d("XXXX", jsonReader.toString())
                    val plants: List<PlantModel> = Gson().fromJson(jsonReader, plantType)
                    dataBase?.plantDao()?.insertPlants(plants)
                }
            }
        }
    }
}
