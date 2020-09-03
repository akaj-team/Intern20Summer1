package com.asiantech.intern20summer1.w9.activitys

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.fragments.SplashFragment
import com.asiantech.intern20summer1.w9.managers.ViewPagerAdapter
import com.asiantech.intern20summer1.w9.models.SharedViewModel
import com.asiantech.intern20summer1.w9.services.AudioService
import kotlinx.android.synthetic.`at-huybui`.activity_music.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is activity class for main activity of music application
 */

class MusicActivity : AppCompatActivity() {

    companion object {
        var service = AudioService()
        var intentService = Intent()
        var bound = false
        var connection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                bound = false
            }

            override fun onServiceConnected(component: ComponentName?, iBinder: IBinder?) {
                val binder = iBinder as AudioService.LocalBinder
                service = binder.getService()
                bound = true
            }
        }
    }

    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        setColorStatusBar()
        handleReplaceFragment(SplashFragment.newInstance(), false)
        intentService = Intent(this, service::class.java)
        bindService(intentService, connection, Context.BIND_AUTO_CREATE)
        startService(intentService)
    }

    override fun onDestroy() {
        unbindService(connection)
        bound = false
        super.onDestroy()
    }

    internal fun initViewPager() {
        val viewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager) }
        containerViewPager?.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun handleReplaceFragment(
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

    private fun setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.apply {
                window.statusBarColor = ContextCompat.getColor(this, R.color.w9_status_bar)
            }
        }
    }
}
