package com.asiantech.intern20summer1.week9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

class MusicMediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_media)

        replaceFragment(SongListFragment(), false)
    }

    internal fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.flMusicMedia, fragment)
        if (isAddToBackStack) {
            trans.addToBackStack(null)
        }
        trans.commit()
    }
}
