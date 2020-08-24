package com.asiantech.intern20summer1.w9.activitys

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.fragments.SplashFragment
import com.asiantech.intern20summer1.w9.services.BackgroundSoundService
import com.asiantech.intern20summer1.w9.services.ViewPagerAdapter
import kotlinx.android.synthetic.`at-huybui`.activity_music.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is activity class for main activity of music application
 */


class MusicActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager) }

    companion object {
        var service = BackgroundSoundService()
        var svc = Intent()
        var bound = false
        var connection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                bound = false
            }

            override fun onServiceConnected(component: ComponentName?, iBinder: IBinder?) {
                val binder = iBinder as BackgroundSoundService.LocalBinder
                service = binder.getService()
                bound = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        setColorStatusBar(R.color.background_white)
        handleReplaceFragment(SplashFragment.newInstance(), false)
        svc = Intent(this, service::class.java)
        bindService(svc, connection, Context.BIND_AUTO_CREATE)
        startService(svc)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        bound = false
    }

    internal fun handleReplaceFragment(
        fragment: Fragment,
        isBackStack: Boolean = false,
        nameBackStack: String = ""
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerMain, fragment)
        if (isBackStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    private fun setColorStatusBar(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.apply {
                window.statusBarColor = ContextCompat.getColor(this, color)
                if (color == R.color.background_white) {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                } else {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                }
            }
        }
    }

    internal fun initViewPager() {
        containerViewPager?.apply {
            adapter = viewPagerAdapter
        }
    }
}
