package com.asiantech.intern20summer1.w9.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.ForegroundService
import com.asiantech.intern20summer1.w9.data.MusicAction
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.w9_fragment_music_player.*

class MusicPlayerFragment : Fragment() {

    companion object {
        private const val ARGUMENT_POSITION_KEY = "position_key"
        internal fun instance(position: Int) = MusicPlayerFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGUMENT_POSITION_KEY, position)
            }
        }
    }

    private var position = 0
    private var musicService = ForegroundService()
    private var isPlaying = false
    private var musicBound = false
    private val songs = mutableListOf<Song>()
    val handler = Handler()
    private lateinit var runnable: Runnable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_music_player, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARGUMENT_POSITION_KEY)
        }
    }

    private var musicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            var binder = p1 as ForegroundService.LocalBinder
            musicService = binder.getService
            circleImageSongMusicPlayer.isSelected = musicService.isPlaying()
            isPlaying = musicService.isPlaying()
            musicBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            musicBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(context, ForegroundService::class.java)
        context?.bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {
        songs.clear()
        songs.addAll(SongData.getSong(requireContext()))
    }

    private fun initListener() {
        handleBackImageViewListener()
        handleSkipPreviousImageViewListener()
        handleLoopImageViewListener()
        handlePlayImageViewListener()
        handleShuffleImageViewListener()
        handleSkipNextImageViewListener()
    }
    private fun setImage() {
        circleImageSongMusicPlayer.setImageURI(songs[position].image)
        Glide.with(requireContext())
            .load(songs[position].image)
            .placeholder(R.drawable.ic_music)
            .into(circleImageSongMusicPlayer)
    }

    private fun handleBackImageViewListener() {
        imgBackMusicPlayer.setOnClickListener {
            Toast.makeText(context, "back", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSkipPreviousImageViewListener() {
        imgSkipPreviousMusicPlayer.setOnClickListener {
            sendAction(MusicAction.PREVIOUS)
        }
    }

    private fun handlePlayImageViewListener(){
        imgPlayMusicPlayer.setOnClickListener {
            onPauseOrPlayMusic()
        }
    }

    private fun handleSkipNextImageViewListener(){
        imgSkipNextMusicPlayer.setOnClickListener {
            sendAction(MusicAction.NEXT)
        }
    }
    private fun handleShuffleImageViewListener(){
        imgShuffleMusicPlayer.setOnClickListener {
            sendAction(MusicAction.SHUFFLE)
        }
    }

    private fun handleLoopImageViewListener(){
        imgReplayMusicPlayer.setOnClickListener {
            sendAction(MusicAction.LOOP)
        }
    }

    private fun onPauseOrPlayMusic() {
        isPlaying = if (!isPlaying) {
            sendAction(MusicAction.PLAY)
            imgPlayMusicPlayer.isSelected = true
            true
        } else {
            sendAction(MusicAction.PAUSE)
            imgPlayMusicPlayer.isSelected = false
            false
        }
    }

    private fun sendAction(action: String) {
        val intent = Intent(requireContext(), ForegroundService::class.java)
        intent.action = action
        intent.putExtra(action, getString(R.string.w9_put_extra_action_value))
        context?.startService(intent)
    }
    private fun handleSeekBarListener() {
        seekBarDurationMusicPlayer.max = songs[position].duration
        imgPlayMusicPlayer.isSelected = true

        seekBarDurationMusicPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                musicService.seekTo(seekBarDurationMusicPlayer.progress)
            }
        })
        runnable = object : Runnable {
            override fun run() {
                position = musicService.getPosition()
                setImage()
                val currentDuration = musicService.getCurrentDuration()
                if (currentDuration != null) {
                    seekBarDurationMusicPlayer.progress = currentDuration
                    tvElapsedTimeLabel.text =
                        SongData.toMin(currentDuration.toLong(), requireContext())
                    tvRemainingTime.text = SongData.toMin(
                        (songs[position].duration - currentDuration).toLong(),
                        requireContext()
                    )
                }
                handler.postDelayed(this, 100)
            }
        }
        handler.post(runnable)
    }
}