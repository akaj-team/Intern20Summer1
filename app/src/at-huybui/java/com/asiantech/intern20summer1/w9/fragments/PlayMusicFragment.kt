package com.asiantech.intern20summer1.w9.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.managers.SongRecyclerAdapter
import com.asiantech.intern20summer1.w9.models.SongItem
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_play_music.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class PlayMusicFragment : Fragment() {

    companion object {
        internal fun newInstance() = PlayMusicFragment()
    }

    private val songLists = mutableListOf<SongItem>()
    private val songAdapter = SongRecyclerAdapter(songLists)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_play_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSongData()
        initSongAdapter()
    }

    private fun initSongAdapter() {
        recyclerViewSongList?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }

    private fun initSongData() {
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songLists.add(SongItem())
        songAdapter.notifyDataSetChanged()
    }
}
