package com.asiantech.intern20summer1.w9.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class PlayMusicFragment : Fragment() {

    companion object {
        internal fun newInstance() = PlayMusicFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_play_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
