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
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.ForegroundService
import com.asiantech.intern20summer1.w9.activity.MusicPlayerActivity
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData
import com.asiantech.intern20summer1.w9.fragment.SongListFragment.Companion.DELAY_TIME
import com.asiantech.intern20summer1.w9.notification.CreateNotification
import kotlinx.android.synthetic.`at-sonnguyen`.w9_fragment_music_player.*
import kotlin.properties.Delegates

@Suppress("DEPRECATION")
class MusicPlayerFragment : Fragment() {

    companion object {
        private const val ARGUMENT_POSITION_KEY = "position_key"
        private const val ARGUMENT_IS_PLAYING_KEY = "is_playing"
        internal fun instance(position: Int, isPlaying: Boolean) = MusicPlayerFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARGUMENT_IS_PLAYING_KEY, isPlaying)
                putInt(ARGUMENT_POSITION_KEY, position)
            }
        }
    }

    private var position = 0
    private var flag = false
    private var musicService = ForegroundService()
    private var isPlayingPlayer by Delegates.notNull<Boolean>()
    private var isLooping = false
    private var isShuffle = false
    private var musicBound = false
    private var notification: CreateNotification? = null
    private val songs = mutableListOf<Song>()
    val handler = Handler()
    private lateinit var runnable: Runnable
    private var isRuning = true
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
            isPlayingPlayer = it.getBoolean(ARGUMENT_IS_PLAYING_KEY)
        }
    }

    private var musicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as ForegroundService.LocalBinder
            musicService = binder.getService
            imgPlayMusicPlayer.isSelected = musicService.isPlaying
            flag = isPlayingPlayer
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

    override fun onStop() {
        super.onStop()
        context?.unbindService(musicServiceConnection)
        isRuning = false
        musicBound = false
    }

    private fun initData() {
        songs.clear()
        songs.addAll(SongData.getSong(requireContext()))
        setData()
    }

    private fun initListener() {
        handleBackImageViewListener()
        handleSkipPreviousImageViewListener()
        handleLoopImageViewListener()
        handlePlayImageViewListener()
        handleShuffleImageViewListener()
        handleSkipNextImageViewListener()
        handleSeekBarListener()
    }

    private fun setData() {
        tvSingerNameMusicPlayer?.text = songs[position].singerName
        tvSongNameMusicPlayer?.text = songs[position].songName
        val bitmap = SongData.convertUriToBitmap(songs[position].uri, requireContext())
        if (bitmap == null) {
            circleImageSongMusicPlayer?.setImageResource(R.drawable.ic_song)
        } else {
            circleImageSongMusicPlayer?.setImageBitmap(bitmap)
        }
    }

    private fun handleBackImageViewListener() {
        imgBackMusicPlayer.setOnClickListener {
            (activity as? MusicPlayerActivity)?.onBackPressed()
        }
    }

    private fun handleSkipPreviousImageViewListener() {
        imgSkipPreviousMusicPlayer.setOnClickListener {
            playPrevious()
            handleSeekBarListener()
        }
    }

    private fun playPrevious() {
        musicService.playPrevious(position)
        position--
        if (position < 0) {
            position = songs.size - 1
        }
        isPlayingPlayer = true
        musicService.isPlaying = true
        imgPlayMusicPlayer.isSelected = true
        flag = true
        createNotification(position)
        setData()
    }

    private fun handlePlayImageViewListener() {
        imgPlayMusicPlayer.setOnClickListener {
            if (flag) {
                onPauseOrPlayMusic()
            } else {
                flag = true
                playSong(position)
            }
        }
    }

    private fun handleSkipNextImageViewListener() {
        imgSkipNextMusicPlayer.setOnClickListener {
            playNext()
        }
    }

    private fun playSong(position: Int) {
        ForegroundService.startService(requireContext(), position)
        imgPlayMusicPlayer.isSelected = true
        isPlayingPlayer = true
        musicService.isPlaying = true
    }

    private fun playNext() {
        musicService.playNext(position)
        position++
        if (position > songs.size - 1) {
            position = 0
        }
        isPlayingPlayer = true
        musicService.isPlaying = true
        flag = true
        imgPlayMusicPlayer.isSelected = isPlayingPlayer
        createNotification(position)
        setData()
        handleSeekBarListener()
    }

    private fun handleShuffleImageViewListener() {
        imgShuffleMusicPlayer.setOnClickListener {
            onShuffle()
        }
    }

    private fun handleLoopImageViewListener() {
        imgReplayMusicPlayer.setOnClickListener {
            onLoop()
        }
    }

    private fun onPauseOrPlayMusic() {
        musicService.onPauseOrPlay()
        isPlayingPlayer = !isPlayingPlayer
        imgPlayMusicPlayer.isSelected = !imgPlayMusicPlayer.isSelected
        musicService.isPlaying = !musicService.isPlaying
        createNotification(position)
    }

    private fun handleSeekBarListener() {
        seekBarDurationMusicPlayer.max = songs[position].duration
        if (seekBarDurationMusicPlayer.progress >= seekBarDurationMusicPlayer.max) {
            if (!isLooping) {
                playNext()
            } else {
                musicService.seekTo(0)
            }
        }
        seekBarDurationMusicPlayer.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.progress?.let {
                    musicService.seekTo(it)
                }
            }
        })
        runnable = object : Runnable {
            override fun run() {
                val currentPosition = this@MusicPlayerFragment.position
                if (isRuning){
                 seekBarDurationMusicPlayer?.max = songs[position].duration
                    position = musicService.getPosition()
                    val currentDuration = musicService.getCurrentDuration()
                    if (currentDuration != null){
                        seekBarDurationMusicPlayer?.progress = currentDuration
                        tvElapsedTimeLabel?.text = SongData.toMin(currentDuration.toLong(),requireContext())
                        tvRemainingTime?.text = SongData.toMin((songs[this@MusicPlayerFragment.position].duration - currentDuration).toLong(),requireContext())
                    }
                    if (this@MusicPlayerFragment.position != currentPosition){
                        this@MusicPlayerFragment.position
                        setData()
                    }else {
                        setData()
                    }
                    handler.postDelayed(this,DELAY_TIME.toLong())
                }

            }
        }
        handler.post(runnable)
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(musicService)
        val notification = notification?.createNotification(songs[position], isPlayingPlayer)
        musicService.startForeground(1, notification)
        isLooping = musicService.isLoop()
        isShuffle = musicService.isShuffle()
    }

    private fun onLoop() {
        if (!isLooping) {
            musicService.isLooping = true
            imgReplayMusicPlayer.isSelected = true
            isLooping = true
            musicService.loopMusic(isLooping)
        } else {
            musicService.isLooping = false
            imgReplayMusicPlayer.isSelected = false
            isLooping = false
            musicService.loopMusic(isLooping)
        }
    }

    private fun onShuffle() {
        if (isShuffle) {
            musicService.isShuffle = false
            imgShuffleMusicPlayer.isSelected = false
            isShuffle = false
        } else {
            imgShuffleMusicPlayer.isSelected = true
            isShuffle = true
            musicService.isShuffle = true
        }
    }
}
