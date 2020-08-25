package com.asiantech.intern20summer1.w9.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
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
        internal fun newInstance(lists: MutableList<Song>) = PlayerFragment().apply {
            lists.toCollection(songLists)
        }
    }

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
        handleSeekBar()
        initButtonView()
        onServiceListener()
    }

    private fun handleSeekBar() {
        service.audioPlayer.duration.let {
            tvDuration_Player?.text = Song().convertDuration(it.toString())
            seekBar?.max = it
        }
        service.songPlaying?.let { song ->
            tvNameSong_Player?.text = song.nameSong
            tvDuration_Player?.text = song.duration
        }
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val current = Song().convertDuration(p1.toString())
                val duration = Song().convertDuration(service.audioPlayer.duration.toString())
                val text = "$current | $duration"
                tvUpdateCurrent_Player?.text = text
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                tvUpdateCurrent_Player.visibility = View.VISIBLE
                isUpdateSeekBar = true
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.let {
                    service.audioPlayer.seekTo(p0.progress)
                }
                tvUpdateCurrent_Player.visibility = View.INVISIBLE
                isUpdateSeekBar = false
            }
        })
    }

    private fun initButtonView() {
        btnPlayer_Player?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onMusicPause()
            } else {
                service.onMusicResume()
            }
        }
        btnLeft_Player?.setOnClickListener {
            service.onMusicPrevious()
        }
        btnRight_Player?.setOnClickListener {
            service.onMusicNext()
        }

        btnReplay_Player?.setOnClickListener {
            if (service.audioPlayer.isLooping) {
                service.audioPlayer.isLooping = false
                btnReplay_Player?.setImageResource(R.drawable.ic_replay_off)
            } else {
                service.audioPlayer.isLooping = true
                btnReplay_Player?.setImageResource(R.drawable.ic_replay_on)
            }
        }

        btnRandom_Player?.setOnClickListener {
            if(service.isRandom){
                btnRandom_Player.setImageResource(R.drawable.ic_random_off)
                service.isRandom = false
                service.onShuffleMusic(songLists)
            }else{
                btnRandom_Player.setImageResource(R.drawable.ic_random_on)
                service.isRandom = true
                service.onShuffleMusic(songLists)
            }
        }
    }

    private fun onServiceListener() {
        service.onUpdateCurrentPosition = {
            tvCurrent_Player?.text = Song().convertDuration(it.toString())
            if (!isUpdateSeekBar) {
                seekBar?.progress = it
            }
        }

        service.onPlayer = { statePlayer ->
            when (statePlayer) {
                AudioService.StatePlayer.START -> {
                    service.songPlaying?.let { song ->
                        val bitmap = Song().getPicture(requireContext(), song)
                        btnPlayer_Player?.setImageResource(R.drawable.ic_pause_button)
                        if(bitmap != null){
                            imgDvd_Player?.setImageBitmap(bitmap)
                        }else{
                            imgDvd_Player?.setImageResource(R.drawable.img_dvd_player)
                        }
                        imgDvd_Player?.startAnim()
                        handleSeekBar()
                    }
                }
                AudioService.StatePlayer.PAUSE -> {
                    btnPlayer_Player?.setImageResource(R.drawable.ic_play_button)
                    imgDvd_Player?.pauseAnim()
                }
                AudioService.StatePlayer.RESUME -> {
                    btnPlayer_Player?.setImageResource(R.drawable.ic_pause_button)
                    imgDvd_Player?.resumeAnim()

                }
            }
        }
    }
}
