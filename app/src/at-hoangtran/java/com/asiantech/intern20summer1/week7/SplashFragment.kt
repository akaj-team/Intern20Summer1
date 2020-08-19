package com.asiantech.intern20summer1.week7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.DownloadImage.Companion.FILE_EXTENSION_KEY
import com.asiantech.intern20summer1.week7.DownloadImage.Companion.IMAGE_FILE_NAME_KEY
import kotlinx.android.synthetic.`at-hoangtran`.fragment_splash.*
import java.util.*

@Suppress("DEPRECATION")
class
SplashFragment : Fragment() {

    companion object {
        private const val TIME_CHECK_DATA = 1L
        private const val TIME_LOAD_DATA = 10L
        private const val TIMER_PERIOD = 100L
        private const val LOAD_DATA_INTERNET = 20L
        internal fun newInstance() = SplashFragment()
    }

    private var counter = 0
    private var appDatabase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProgressBar()
    }

    private fun loadProgressBar() {
        var plants = appDatabase?.getPlantDAO()?.getPlants()
        var count = 0L
        Timer().schedule(object : TimerTask() {
            override fun run() {
                count++
                progress_bar?.progress = count.toInt()
                when (count) {
                    TIME_CHECK_DATA -> {
                        if (plants?.size != 0) {
                            handleProgressBar()
                            cancel()
                        }
                    }
                    TIME_LOAD_DATA -> {
                        plants = appDatabase?.getPlantDAO()?.getPlants()
                    }
                    LOAD_DATA_INTERNET -> {
                        plants?.forEach { plant ->
                            plant.id?.let {
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

    private fun handleProgressBar() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                counter++
                progress_bar.progress = counter
                if (counter.toLong() == TIMER_PERIOD) {
                    changeActivity()
                    cancel()
                }
            }
        }, 0, TIMER_PERIOD)
    }

    private fun checkUser(): Boolean {
        val user = appDatabase?.getUserDAO()?.getUser()
        return user != null
    }

    private fun changeActivity() {
        if (checkUser()) {
            val intent = Intent(activity, GardenActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        } else {
            (activity as SplashActivity).showRegisterFragment()
        }
    }

    private fun updateImageUrl(plants: List<Plant>?) {
        val path = requireContext().getDir(IMAGE_FILE_NAME_KEY, Context.MODE_PRIVATE)
        plants?.forEach { plant ->
            plant.id?.let {
                appDatabase?.getPlantDAO()?.updateImageUrl("$path/${it}${FILE_EXTENSION_KEY}", it)
            }
        }
    }
}
