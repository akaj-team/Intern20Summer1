package com.asiantech.intern20summer1.week7.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.other.ID_KEY
import com.asiantech.intern20summer1.week7.other.USER_DATA_PREFS
import kotlinx.android.synthetic.`at-longphan`.activity_splash_w7.*

class SplashWeekSevenActivity : AppCompatActivity() {

    private var hasAccount = false
    private var database: PlantRoomDatabase? = null

    companion object {

        private const val MAX_PROGRESS = 100
        private const val PROGRESS_PER_LOOP = 20
        private const val SLEEP_TIME: Long = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_w7)
        database = PlantRoomDatabase.getDatabase(this)

        val sharePref = getSharedPreferences(USER_DATA_PREFS, Context.MODE_PRIVATE)
        val id = sharePref.getInt(ID_KEY, -1)

        if (database?.userDao()?.isExist(id) != 0) {
            hasAccount = true
        }

        Thread(Runnable {
            runProgressBar()
            startApp()
            finish()
        }).start()
    }

    private fun runProgressBar() {
        var progress = 0
        while (progress <= MAX_PROGRESS) {
            Thread.sleep(SLEEP_TIME)
            progressBarSplashActivityW7?.progress = progress
            progress += PROGRESS_PER_LOOP
        }
    }

    private fun startApp() {
        val intent = if (hasAccount) {
            Intent(this, HomeWeekSevenActivity::class.java)
        } else {
            Intent(this, RegisterWeekSevenActivity::class.java)
        }
        startActivity(intent)
    }
}
