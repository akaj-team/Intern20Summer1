package com.asiantech.intern20summer1.week9.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
}
