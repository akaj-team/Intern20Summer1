package com.asiantech.intern20summer1.week7

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-phuongle`.activity_splash.*

class SplashActivity : AppCompatActivity() {
    companion object {
        const val MAX_PROGESS = 5000
        const val ONE_HUNDRED = 100L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handleProgessBar()
    }

    private fun switchActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleProgessBar() {
        var process: Long = 0
        val handler = Handler()

        progressBarSplash?.max = MAX_PROGESS
        Thread(Runnable {
            while (process < MAX_PROGESS) {
                process += MAX_PROGESS / ONE_HUNDRED
                // Update the progress bar and display the current value
                handler.post {
                    progressBarSplash?.progress = process.toInt()
                    if (progressBarSplash?.progress == MAX_PROGESS) {
                        switchActivity()
                    }
                }
                try {
                    Thread.sleep(MAX_PROGESS / ONE_HUNDRED)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }).start()
    }
}
