package com.asiantech.intern20summer1.fragment.w9

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.w9.Music
import com.asiantech.intern20summer1.data.w9.MusicAction
import com.asiantech.intern20summer1.data.w9.MusicData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-vuongphan`.fragment_auto_play_music.*
import kotlinx.android.synthetic.`at-vuongphan`.w9_fragment_list_music.*

@Suppress("DEPRECATION")
class MusicScreenFragment : Fragment(), View.OnClickListener {
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private var musicService = MusicService()
    private var musicBound = false
    private var position = 0
    private var isPlaying = false
    private lateinit var audioManager: AudioManager
    private val music = mutableListOf<Music>()
    private var musicConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            musicBound = false
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            musicService = (p1 as MusicService.LocalBinder).getService
            btnPlayPause?.isSelected = musicService.isPlaying()
            btnNext?.isSelected = true
            btnBack?.isSelected = true
            musicBound = true
        }
    }

    companion object {
        private const val DELAY_TIME: Long = 1000
        private const val ARG_POSITION = "name"
        fun newInstance(position: Int) = MusicScreenFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auto_play_music, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onStart() {
        super.onStart()
        context?.bindService(
            Intent(context, MusicService::class.java),
            musicConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnShuffle?.isSelected = false
        btnPausePlay?.isSelected = isPlaying
        startRotatingImage()
        initData()
        btnNextMain?.setOnClickListener(this)
        btnPausePlay?.setOnClickListener(this)
        btnPreviousMain?.setOnClickListener(this)
        btnShuffle?.setOnClickListener(this)
        btnLoop?.setOnClickListener(this)
        handleSeekBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }

    override fun onClick(view: View?) {
        when (view) {
            btnNextMain -> {
                sendAction(MusicAction.NEXT)
                btnPausePlay.setBackgroundResource(R.drawable.ic_pause_circle_outline_red)
            }
            btnPausePlay -> {
                onPausePlayMusic()
                initPlayPauseButton()
            }
            btnPreviousMain -> {
                sendAction(MusicAction.PREVIOUS)
                btnPausePlay.setBackgroundResource(R.drawable.ic_pause_circle_outline_red)
            }
            btnShuffle -> {
                sendAction(MusicAction.SHUFFLE)
            }
            btnLoop -> {
                sendAction(MusicAction.LOOP)
            }
        }
    }


    private fun setImage() {
        imgMusic.setImageURI(music[position].image)
        Glide.with(requireContext())
            .load(music[position].image)
            .placeholder(R.drawable.ic_background_auto_play)
            .into(imgMusic)
    }

    private fun sendAction(action: String) {
        val intent = Intent(requireContext(), MusicService::class.java)
        intent.action = action
        intent.putExtra(action, "1")
        context?.startService(intent)
    }

    private fun startRotatingImage() {
        val startRotateAnimation: Animation =
            AnimationUtils.loadAnimation(context, R.anim.animation_rotate)
        imgMusic.startAnimation(startRotateAnimation)
    }

    private fun initData() {
        music.clear()
        music.addAll(MusicData.getMusic(requireContext()))
    }

    private fun onPausePlayMusic() {
        isPlaying = if (!isPlaying) {
            sendAction(MusicAction.PLAY)
            btnPausePlay.isSelected = true
            true
        } else {
            sendAction(MusicAction.PAUSE)
            btnPausePlay?.isSelected = false
            false
        }
    }

    private fun handleSeekBar() {
        val maxSeekBar = musicService.getTime()
        if (maxSeekBar != null) {
            seekBar.max = maxSeekBar
        }
        btnPausePlay.isSelected = true

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                musicService.seekTo(seekBar.progress)
            }
        })
        runnable = object : Runnable {
            override fun run() {
                position = musicService.getPosition()
                setImage()
                val currentDuration = musicService.getCurrentDuration()
                val current = musicService.getTime()
                if (currentDuration != null) {
                    seekBar.progress = currentDuration
                    tvDuration.text =
                        current?.toLong()?.let { MusicData.toMin(it, requireContext()) }
                    tvCurrentDuration.text =
                        MusicData.toMin(currentDuration.toLong(), requireContext())
                }
                handler.postDelayed(this, DELAY_TIME)
            }
        }
        handler.post(runnable)
    }

    private fun initPlayPauseButton() {
        if (isPlaying) {
            btnPausePlay.setBackgroundResource(R.drawable.ic_pause_circle_outline_red)
        } else {
            btnPausePlay.setBackgroundResource(R.drawable.ic_play_circle_outline_red)
        }
    }
}
