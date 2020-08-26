package com.asiantech.intern20summer1.week9.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.fragments.ListSongFragment

class MusicMediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_media)
        openSongPlayFragment()
    }

    private fun openSongPlayFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flMusicContainer, ListSongFragment.newInstance()).commit()
    }

    internal fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flMusicContainer, fragment, null)
        if (isAddToBackStack) {
            ft.addToBackStack(null)
        }
        ft.commit()
    }
}
