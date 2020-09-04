package com.asiantech.intern20summer1.w9.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import com.asiantech.intern20summer1.w9.models.SharedViewModel
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.services.AudioService
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_player.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/23/20
 * This is fragment class for splash fragment of player application
 */

class PlayerFragment : Fragment() {

    companion object {
        internal fun newInstance() = PlayerFragment()
        private const val DEFAULT_DURATION = "00:00"
    }

    private lateinit var viewModel: SharedViewModel
    private var songLists = mutableListOf<Song>()
    private val service = MusicActivity.service
    private var isUpdateSeekBar = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    private fun initData() {
        viewModel = ViewModelProvider(activity as MusicActivity).get(SharedViewModel::class.java)
        viewModel.songLists.value?.toCollection(songLists)
    }

    private fun initView() {
        initPlayerView()
        initPlayerListener()
    }

    private fun initPlayerListener() {
        initPlayerClickListener()
        initServiceListener()
        initHandleSeekBar()
    }

    private fun initPlayerClickListener() {
        listenerClickButtonControl()
        listenerClickButtonRandomAndLoop()
    }

    private fun initPlayerView() {
        imgDvd?.createAnim()
        tvNameSong?.isSelected = true // set auto run text view
        service.songPlaying?.let { song ->
            tvNameSong?.text = song.nameSong
            tvSinger?.text = song.singer
            imgDvd?.setImageBitmap(song.getPicture(requireContext(), false))
            if (service.audioPlayer.isPlaying) {
                btnPlay?.setImageResource(R.drawable.ic_pause_button)
                imgDvd?.startAnim()
            } else {
                btnPlay?.setImageResource(R.drawable.ic_play_button)
            }
        }
    }

    private fun initHandleSeekBar() {
        tvDuration?.text = DEFAULT_DURATION
        tvCurrent?.text = DEFAULT_DURATION
        if (service.isPlaying) {
            tvCurrent?.text = Song().convertDuration(service.audioPlayer.currentPosition.toString())
            service.audioPlayer.duration.let { duration ->
                tvDuration?.text = Song().convertDuration(duration.toString())
                seekBar?.max = duration
            }
            service.songPlaying?.let { song ->
                tvDuration?.text = song.duration
            }
            seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    val current = Song().convertDuration(p1.toString())
                    val duration = Song().convertDuration(service.audioPlayer.duration.toString())
                    val text = "$current | $duration"
                    tvUpdateCurrent?.text = text
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    tvUpdateCurrent.visibility = View.VISIBLE
                    isUpdateSeekBar = true
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let {
                        service.audioPlayer.seekTo(seekBar.progress)
                    }
                    tvUpdateCurrent.visibility = View.INVISIBLE
                    isUpdateSeekBar = false
                }
            })
        }
    }

    private fun initServiceListener() {
        service.onUpdateCurrentPosition = {
            if (service.audioPlayer.isPlaying) {
                tvCurrent?.text = Song().convertDuration(it.toString())
            }
            if (!isUpdateSeekBar) {
                seekBar?.progress = it
            }
        }

        service.onPlayer = { statePlayer ->
            when (statePlayer) {
                AudioService.StatePlayer.START -> {
                    service.songPlaying?.let { song ->
                        tvNameSong?.text = song.nameSong
                        tvSinger?.text = song.singer
                        btnPlay?.setImageResource(R.drawable.ic_pause_button)
                        val bitmap = song.getPicture(requireContext(), false)
                        imgDvd?.setImageBitmap(bitmap)
                        imgDvd?.startAnim()
                        initHandleSeekBar()
                    }
                }
                AudioService.StatePlayer.PAUSE -> {
                    btnPlay?.setImageResource(R.drawable.ic_play_button)
                    imgDvd?.pauseAnim()
                }
                AudioService.StatePlayer.RESUME -> {
                    btnPlay?.setImageResource(R.drawable.ic_pause_button)
                    imgDvd?.resumeAnim()

                }
            }
        }
    }

    private fun listenerClickButtonControl() {
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
    }

    private fun listenerClickButtonRandomAndLoop() {
        btnReplay?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                if (service.audioPlayer.isLooping) {
                    service.audioPlayer.isLooping = false
                    btnReplay?.setImageResource(R.drawable.ic_replay_off)
                } else {
                    service.audioPlayer.isLooping = true
                    btnReplay?.setImageResource(R.drawable.ic_replay_on)
                }
            }
        }
        btnRandom?.setOnClickListener {
            if (service.isRandom) {
                btnRandom.setImageResource(R.drawable.ic_random_off)
                service.isRandom = false
                service.onShuffleMusic(songLists)
            } else {
                btnRandom.setImageResource(R.drawable.ic_random_on)
                service.isRandom = true
                service.onShuffleMusic(songLists)
            }
        }
    }
}
