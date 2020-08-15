package com.asiantech.intern20summer1.week7

import android.content.Context
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
        const val REGISTER_SHARED_PREF_FILE_NAME = "register"
        const val AVATAR_KEY = "avatar"
        const val USER_NAME_KEY = "user_name"
        const val UNIVERSITY_KEY = "university"
        const val HOME_TOWN_KEY = "home_town"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handleProgessBar()
    }

    private fun switchActivity() {
        if (isNotRegistered()) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
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

    private fun isNotRegistered(): Boolean {
        val sharedPreferences =
            getSharedPreferences(REGISTER_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)

        val userAvatar = sharedPreferences.getString(AVATAR_KEY, null)
        val userName = sharedPreferences.getString(USER_NAME_KEY, null)
        val userUniversity = sharedPreferences.getString(UNIVERSITY_KEY, null)
        val userHomeTown = sharedPreferences.getString(HOME_TOWN_KEY, null)

        return userAvatar.isNullOrEmpty()
                && userName.isNullOrEmpty()
                && userUniversity.isNullOrEmpty()
                && userHomeTown.isNullOrEmpty()
    }
}
