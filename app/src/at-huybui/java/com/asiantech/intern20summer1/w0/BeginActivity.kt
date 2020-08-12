package com.asiantech.intern20summer1.w0

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w3.MainActivity
import com.asiantech.intern20summer1.w4.activity.SplashActivity
import com.asiantech.intern20summer1.w6.firstactivity.ViewPagerOneActivity
import com.asiantech.intern20summer1.w7.launcher.activity.LauncherFarmActivity
import kotlinx.android.synthetic.`at-huybui`.activity_begin.*

class BeginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin)
        handleStatusBarFollowSdk()
        initListener()
    }

    private fun initListener() {
        btnBeginW6?.setOnClickListener {
            val intent = Intent(this, ViewPagerOneActivity::class.java)
            startActivity(intent)
        }

        btnBeginW3?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnBeginW4?.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }

        btnBeginW7.setOnClickListener {
            val intent = Intent(this, LauncherFarmActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * This function will change color of status bar follow api of device
     */
    private fun handleStatusBarFollowSdk() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
        }
    }
}
