package com.asiantech.intern20summer1.week7

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.fragment_splash.*
import java.util.*

@Suppress("DEPRECATION")
class SplashFragment : Fragment() {
    var counter = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        handleProgressBar()
        Handler().postDelayed(mRunnable,0)
    }

    private fun handleProgressBar() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                counter++
                progress_bar.progress = counter
                if (counter == 100) {
                    Timer().cancel()
                }
            }

        }, 0, 50)

    }

    private val mRunnable: Runnable = Runnable {
        Thread(Runnable {
            while (counter < 100) {
                try {
                    counter += 1
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progress_bar.progress = counter
            }
            (activity as SplashActivity).showRegisterFragment()
        }).start()
    }
}
