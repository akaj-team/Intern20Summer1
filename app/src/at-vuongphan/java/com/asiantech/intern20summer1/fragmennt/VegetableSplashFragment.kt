package com.asiantech.intern20summer1.fragmennt

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
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB
import com.asiantech.intern20summer1.fragmennt.VegetableRegisterFragment.Companion.SHARED_FILE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.`at-vuongphan`.w7_splash_fragment.*
import java.util.concurrent.Executors

class VegetableSplashFragment : Fragment() {
    private var dataBase: VegetableDB? = null

    companion object {
        const val START = 5000L
        const val STEP_PROGRESS_BAR = 120L
        const val STEP_FINISH = 100
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
       // dataBase = VegetableDB.dataBaseConnect(requireContext())
        initProgressBar()
    }

    private fun initProgressBar() {
        progressSplash?.progress = 0
        val timer = object : CountDownTimer(
            START,
            START / STEP_PROGRESS_BAR
        ) {
            override fun onTick(p0: Long) {
                val user = dataBase?.userDao()?.getUser()
                progressSplash?.progress = progressSplash.progress + 1
//                when (progressSplash?.progress) {
//                    5 -> {
//                        saveDataFromJsonFile(requireContext())
//                    }
//                }
                if (progressSplash?.progress == STEP_FINISH) {
                    this.cancel()
                    if (checkUser()) {
                        (activity as? VegetableSplashActivity)?.openFragment(
                            VegetableRegisterFragment.newInstance()
                        )
                    } else {
                        activity?.startActivity(
                            Intent(
                                activity,
                                VegetableFarmMainActivity::class.java
                            )
                        )
                        activity?.finish()
                    }
                }
            }

            override fun onFinish() {
            }
        }
        timer.start()
    }

    private fun checkUser(): Boolean {
        val sharedRef = activity?.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE)
        if (sharedRef?.getString("userName", null) == null) {
            return true
        }
        return false
    }

    private fun saveDataFromJsonFile(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open("plants.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    dataBase?.plantDao()?.insertPlants(plants)
                }
            }
        }
    }
}
