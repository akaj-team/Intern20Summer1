package com.asiantech.intern20summer1.week7.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.extensions.DownloadImage
import com.asiantech.intern20summer1.week7.extensions.DownloadImage.Companion.FILE_TAIL_KEY
import com.asiantech.intern20summer1.week7.extensions.DownloadImage.Companion.NAME_FILE_IMAGE_KEY
import com.asiantech.intern20summer1.week7.models.Plant
import com.asiantech.intern20summer1.week7.views.HomeActivity
import com.asiantech.intern20summer1.week7.views.LauncherActivity
import kotlinx.android.synthetic.`at-linhle`.fragment_splash.*
import java.util.*

class SplashFragment : Fragment() {

    companion object {
        private const val TIME_CHECK_DATA = 1L
        private const val TIME_LOAD_DATA = 10L
        private const val TIMER_PERIOD = 100L
        private const val LOAD_DATA_INTERNET = 20L
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
        loadProgressBar()
    }

    private fun loadProgressBar() {
        var plants = appDataBase?.getPlantDao()?.getPlants()
        var count = 0L
        Timer().schedule(object : TimerTask() {
            override fun run() {
                count++
                progressBar?.progress = count.toInt()
                when (count) {
                    TIME_CHECK_DATA -> {
                        if (plants?.size != 0) {
                            loadProgressBarAgain()
                            cancel()
                        }
                    }
                    TIME_LOAD_DATA -> {
                        plants = appDataBase?.getPlantDao()?.getPlants()
                    }
                    LOAD_DATA_INTERNET -> {
                        plants?.forEach { plant ->
                            plant.plantId?.let {
                                DownloadImage(requireContext(), it).execute(plant.imageUrl)
                            }
                        }
                    }
                    TIMER_PERIOD -> {
                        updateImageUrl(plants)
                        changeActivity()
                        cancel()
                    }
                }
            }
        }, 0, TIMER_PERIOD)
    }

    private fun loadProgressBarAgain() {
        var count = progressBar.progress
        Timer().schedule(object : TimerTask() {
            override fun run() {
                count++
                progressBar.progress = count
                if (count.toLong() == TIMER_PERIOD) {
                    changeActivity()
                    cancel()
                }
            }
        }, 0, TIMER_PERIOD)
    }

    private fun isUserInvalid(): Boolean {
        val user = appDataBase?.getUserDao()?.getUsers()
        return user == null
    }

    private fun changeActivity() {
        if (isUserInvalid()) {
            (activity as LauncherActivity).openRegisterFragment()
        } else {
            val intent = Intent(activity, HomeActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    private fun updateImageUrl(plants: List<Plant>?) {
        val path = requireContext().getDir(NAME_FILE_IMAGE_KEY, Context.MODE_PRIVATE)
        plants?.forEach { plant ->
            plant.plantId?.let {
                appDataBase?.getPlantDao()?.updateImageUrl("$path/${it}${FILE_TAIL_KEY}", it)
            }
        }
    }
}
