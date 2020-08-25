package com.asiantech.intern20summer1.w9.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import com.asiantech.intern20summer1.w9.managers.SongRecyclerAdapter
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.services.AudioService
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_music.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class MusicFragment : Fragment() {

    companion object {
        internal fun newInstance(songListsNew: MutableList<Song>) = MusicFragment().apply {
            songListsNew.toCollection(songLists)
        }
    }

    private var songLists = mutableListOf<Song>()
    private val songAdapter = SongRecyclerAdapter(songLists)
    private val service = MusicActivity.service

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSongData()
        initSongAdapter()
        initView()
        onServiceListener()
        initButtonListener()
    }

    private fun initView() {
        initPlayerBar(service)
        tvNameSongPlayerBar.isSelected = true
    }

    private fun initButtonListener() {
        imgPlayer?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onMusicPause()
            } else {
                service.onMusicResume()
            }
        }
        imgLeftNext?.setOnClickListener {
            service.onMusicPrevious()
        }
        imgRightNext?.setOnClickListener {
            service.onMusicNext()
        }
    }

    private fun initSongAdapter() {
        recyclerViewSongList?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        songAdapter.onItemClick = { position ->
            service.songPosition = position
            service.onMusicStart()
        }
    }

    private fun initSongData() {
        service.songLists.clear()
        songLists.toCollection(service.songLists)
    }

    private fun initPlayerBar(sv: AudioService) {
        sv.songPlaying?.let {
            if (sv.audioPlayer.isPlaying) {
                imgPlayer?.setImageResource(R.drawable.ic_pause_button)
                val bitmap = Song().getPicture(requireContext(), it)
                if (bitmap != null) {
                    imgDvdPlayerBar?.setImageBitmap(bitmap)
                } else {
                    imgDvdPlayerBar?.setImageResource(R.drawable.img_dvd_player)
                }
                imgDvdPlayerBar?.startAnim()
            } else {
                imgPlayer?.setImageResource(R.drawable.ic_play_button)
                val bitmap = Song().getPicture(requireContext(), it)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                imgDvdPlayerBar?.endAnim()
            }
        }
    }

    private fun onServiceListener() {
        service.onPlayerBar = { statePlayer ->
            when (statePlayer) {
                AudioService.StatePlayer.START -> {
                    service.songPlaying?.let { song ->
                        val bitmap = Song().getPicture(requireContext(), song)
                        if (bitmap == null) {
                            imgDvdPlayerBar?.setImageResource(R.drawable.ic_dvd_player)
                        } else {
                            imgDvdPlayerBar?.setImageBitmap(bitmap)
                        }
                        imgPlayer?.setImageResource(R.drawable.ic_pause_button)
                        imgDvdPlayerBar?.startAnim()
                        tvNameSongPlayerBar?.text = song.nameSong
                    }
                }
                AudioService.StatePlayer.RESUME -> {
                    imgDvdPlayerBar?.resumeAnim()
                    imgPlayer?.setImageResource(R.drawable.ic_pause_button)
                }
                AudioService.StatePlayer.PAUSE -> {
                    imgDvdPlayerBar?.pauseAnim()
                    imgPlayer?.setImageResource(R.drawable.ic_play_button)
                }
            }
        }

        service.onShuffleSong = {
            songLists.clear()
            service.songLists.toCollection(songLists)
            songLists.forEach {
                d("ZZZ", "xxx ->" + it.contentUri)
            }
            songAdapter.notifyDataSetChanged()
        }
    }
}
