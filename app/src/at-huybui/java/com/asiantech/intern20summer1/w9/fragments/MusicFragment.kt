package com.asiantech.intern20summer1.w9.fragments

import android.os.Bundle
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
        initView()
        initAudioService()
    }

    override fun onDestroy() {
        super.onDestroy()
        service.isOpenApp = false  // clear state is open app
    }

    private fun initView() {
        initPlayerBar()
        initMusicRecycler()
    }

    private fun initPlayerBar() {
        initPlayerBarClickListener()
        initPlayerBarView()
    }

    private fun initPlayerBarView() {
        tvSongName_Music.isSelected = true // set auto run text view
        service.songPlaying?.let { song ->
            tvSongName_Music?.text = song.nameSong
            tvSinger_Music?.text = song.singer
            val bitmap = song.getPicture(requireContext())
            imgDvd_Music?.setImageBitmap(bitmap)
            if (service.audioPlayer.isPlaying) {
                btnPlay_Music?.setImageResource(R.drawable.ic_pause_button)
                imgDvd_Music?.startAnim()
            } else {
                btnPlay_Music?.setImageResource(R.drawable.ic_play_button)
                imgDvd_Music?.endAnim()
            }
        }
    }

    private fun initMusicRecycler() {
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

    private fun initPlayerBarClickListener() {
        btnPlay_Music?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onMusicPause()
            } else {
                service.onMusicResume()
            }
        }
        btnPrevious_Music?.setOnClickListener {
            service.onMusicPrevious()
        }
        btnNext_Music?.setOnClickListener {
            service.onMusicNext()
        }
    }

    private fun initAudioService() {
        if (service.songLists.size < 1) {
            songLists.toCollection(service.songLists)
        }
        service.isOpenApp = true // set status is open app in onCreate
        //init listener from service at here
        service.onPlayerBar = { statePlayer ->
            when (statePlayer) {
                AudioService.StatePlayer.START -> {
                    service.songPlaying?.let { song ->
                        val bitmap = song.getPicture(requireContext())
                        btnPlay_Music?.setImageResource(R.drawable.ic_pause_button)
                        imgDvd_Music?.setImageBitmap(bitmap)
                        imgDvd_Music?.startAnim()
                        tvSongName_Music?.text = song.nameSong
                        tvSinger_Music?.text = song.singer
                    }
                }
                AudioService.StatePlayer.RESUME -> {
                    imgDvd_Music?.resumeAnim()
                    btnPlay_Music?.setImageResource(R.drawable.ic_pause_button)
                }
                AudioService.StatePlayer.PAUSE -> {
                    imgDvd_Music?.pauseAnim()
                    btnPlay_Music?.setImageResource(R.drawable.ic_play_button)
                }
            }
        }

        service.onShuffleSong = {
            songLists.clear()
            service.songLists.toCollection(songLists)
            songAdapter.notifyDataSetChanged()
        }
    }
}
