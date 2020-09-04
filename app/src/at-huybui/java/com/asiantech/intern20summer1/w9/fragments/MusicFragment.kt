package com.asiantech.intern20summer1.w9.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import com.asiantech.intern20summer1.w9.managers.SongRecyclerAdapter
import com.asiantech.intern20summer1.w9.models.SharedViewModel
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.services.AudioService
import kotlinx.android.synthetic.`at-huybui`.activity_music.*
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_music.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class MusicFragment : Fragment() {

    companion object {
        internal fun newInstance() = MusicFragment()
    }

    private lateinit var viewModel: SharedViewModel
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
        initData()
        initView()
        initAudioService()
    }

    override fun onDestroy() {
        super.onDestroy()
        service.isOpenApp = false  // clear state is open app
    }

    private fun initData() {
        viewModel = ViewModelProvider(activity as MusicActivity).get(SharedViewModel::class.java)
        viewModel.songLists.value?.toCollection(songLists)
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
        imgDvd?.createAnim()
        tvSongName?.isSelected = true // set auto run text view
        service.songPlaying?.let { song ->
            tvSongName?.text = song.nameSong
            tvSinger?.text = song.singer
            val bitmap = song.getPicture(requireContext())
            imgDvd?.setImageBitmap(bitmap)
            if (service.audioPlayer.isPlaying) {
                btnPlay?.setImageResource(R.drawable.ic_pause_button)
                imgDvd?.startAnim()
            } else {
                btnPlay?.setImageResource(R.drawable.ic_play_button)
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
            (activity as MusicActivity).containerViewPager.setCurrentItem(1, true)
        }
    }

    private fun initPlayerBarClickListener() {
        btnPlay?.setOnClickListener {
            if (service.isPlaying) {
                if (service.audioPlayer.isPlaying) {
                    service.onMusicPause()
                } else {
                    service.onMusicResume()
                }
            }
        }
        btnPrevious?.setOnClickListener {
            if (service.isPlaying) {
                service.onMusicPrevious()
            }
        }
        btnNext?.setOnClickListener {
            if (service.isPlaying) {
                service.onMusicNext()
            }
        }

        rlPlayerBar?.setOnClickListener {
            (activity as MusicActivity).containerViewPager.setCurrentItem(1, true)
        }
    }

    private fun initAudioService() {
        if (service.songLists.isNullOrEmpty()) {
            songLists.toCollection(service.songLists)
        }
        service.isOpenApp = true // set status is open app in onCreate
        //init listener from service at here
        service.onPlayerBar = { statePlayer ->
            when (statePlayer) {
                AudioService.StatePlayer.START -> {
                    service.songPlaying?.let { song ->
                        btnPlay?.setImageResource(R.drawable.ic_pause_button)
                        val bitmap = song.getPicture(requireContext())
                        imgDvd?.setImageBitmap(bitmap)
                        imgDvd?.startAnim()
                        tvSongName?.text = song.nameSong
                        tvSinger?.text = song.singer
                    }
                }
                AudioService.StatePlayer.RESUME -> {
                    imgDvd?.resumeAnim()
                    btnPlay?.setImageResource(R.drawable.ic_pause_button)
                }
                AudioService.StatePlayer.PAUSE -> {
                    imgDvd?.pauseAnim()
                    btnPlay?.setImageResource(R.drawable.ic_play_button)
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
