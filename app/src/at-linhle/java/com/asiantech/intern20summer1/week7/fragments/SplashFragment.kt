package com.asiantech.intern20summer1.week7.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.extensions.DownloadImage
import com.asiantech.intern20summer1.week7.fragments.RegisterFragment.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.week7.models.Plant
import com.asiantech.intern20summer1.week7.views.HomeActivity
import com.asiantech.intern20summer1.week7.views.LauncherActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.`at-linhle`.fragment_splash.*
import java.util.*
import java.util.concurrent.Executors

class SplashFragment : Fragment() {

    companion object {
        private const val TIME_CHECK_DATA = 10L
        private const val TIME_LOAD_DATA = 20L
        private const val TIMER_PERIOD = 100L
        private const val LOAD_DATA_INTERNET = 30L
    }

    private var appDataBase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDataBase = AppDatabase.getInstance(requireContext())
        loadProgressbar()
    }

    private fun loadProgressbar() {
        var plants = appDataBase?.getPlantDao()?.getPlants()
        var count = 0L
        Timer().schedule(object : TimerTask() {
            override fun run() {
                count++
                progressBar?.progress = count.toInt()
                when (count) {
                    TIME_CHECK_DATA -> {
                        if (plants?.size == 0) {
                            buildPlantData(requireContext())
                        }
                    }
//                    TIME_LOAD_DATA -> {
//                        plants = appDataBase?.getPlantDao()?.getPlants()
//                    }
//                    LOAD_DATA_INTERNET -> {
//                        plants?.forEach { plant ->
//                            plant.plantId?.let {
//                                DownloadImage(requireContext(), it).execute(plant.imageUrl)
//                            }
//                        }
//                    }
                    TIMER_PERIOD -> {
                        changeActivity()
                        cancel()
                    }
                }
            }
        }, 0, TIMER_PERIOD)
    }

    private fun checkFile(): Boolean {
        val sharedRef = activity?.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (sharedRef?.getString("userName", null) == null) {
            return true
        }
        return false
    }

    private fun changeActivity() {
        if (checkFile()) {
            (activity as LauncherActivity).openRegisterFragment()
        } else {
            val intent = Intent(activity, HomeActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    private fun buildPlantData(context: Context) {
        Executors.newFixedThreadPool(2).execute {
            context.assets.open("plants.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plants: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    appDataBase?.getPlantDao()?.insertPlants(plants)
                }
            }
        }
    }
}
