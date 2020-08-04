package com.asiantech.intern20summer1.w7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_splash_farm.*

class SplashFarmActivity : AppCompatActivity() {

    companion object{
        private const val SPLASH_TIMER = 5000L
        private const val PROGRESS_BAR_STEP = 100L
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_farm)

        progressBarFarm.progress = 0
        val timer = object: CountDownTimer(SPLASH_TIMER, SPLASH_TIMER/ PROGRESS_BAR_STEP) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarFarm.progress = progressBarFarm.progress + 1
            }
            override fun onFinish() {
                Toast.makeText(this@SplashFarmActivity,"ok",Toast.LENGTH_SHORT).show()
            }
        }
        timer.start()

    }


}
