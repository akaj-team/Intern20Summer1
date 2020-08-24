package com.asiantech.intern20summer1.activity.w9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.w9.ListFragmentMusic

class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w9_activity_music)
        replacePlayListFragment(0, false)
    }

    internal fun handleReplaceFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        parent: Int,
        nameBackStack: String = ""
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(parent, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    internal fun replacePlayListFragment(position: Int, isPlaying: Boolean) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frContainer,
                ListFragmentMusic.newInstance(position, isPlaying).apply {
                },
                null
            )
            .addToBackStack(null)
            .commit()
    }
}
