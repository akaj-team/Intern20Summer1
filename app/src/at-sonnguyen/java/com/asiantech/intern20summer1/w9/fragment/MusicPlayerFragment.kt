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
import com.asiantech.intern20summer1.w9.notification.CreateNotification
import kotlinx.android.synthetic.`at-sonnguyen`.w9_fragment_music_player.*

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
    private var isLooping = false
    private var isShuffle = false
    private var musicBound = false
    private var notification: CreateNotification? = null
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

    override fun onStop() {
        super.onStop()
        context?.unbindService(musicServiceConnection)
        musicBound = false
    }

    private fun initData() {
        songs.clear()
        songs.addAll(SongData.getSong(requireContext()))
        setData(requireContext())
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

    private fun setData(context: Context) {
        tvSingerNameMusicPlayer.text = songs[position].singerName
        tvSongNameMusicPlayer.text = songs[position].songName
        circleImageSongMusicPlayer.setImageURI(songs[position].image)
//        Glide.with(requireContext())
//            .load(songs[position].image)
//            .placeholder(R.drawable.ic_song)
//            .into(circleImageSongMusicPlayer)
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
        isPlaying = true
        createNotification(position)
        setData(requireContext())
    }

    private fun handlePlayImageViewListener() {
        imgPlayMusicPlayer.setOnClickListener {
            onPauseOrPlayMusic()
        }
    }

    private fun handleSkipNextImageViewListener() {
        imgSkipNextMusicPlayer.setOnClickListener {
            playNext()
        }
    }

    private fun playNext() {
        musicService.playNext(position)
        position++
        if (position > songs.size - 1) {
            position = 0
        }
        isPlaying = true
        createNotification(position)
        setData(requireContext())
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
//        isPlaying = if (!isPlaying) {
//            sendAction(MusicAction.PLAY)
//            imgPlayMusicPlayer.isSelected = true
//            true
//        } else {
//            sendAction(MusicAction.PAUSE)
//            imgPlayMusicPlayer.isSelected = false
//            false
//        }

        if (isPlaying) {
            imgPlayMusicPlayer.isSelected = false
            musicService.onPauseOrPlay()
            isPlaying = false
        } else {
            imgPlayMusicPlayer.isSelected = true
            musicService.onPauseOrPlay()
            isPlaying = true
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
                position = musicService.getPosition()
                val currentDuration = musicService.getCurrentDuration()
                if (currentDuration != null) {
                    seekBarDurationMusicPlayer?.progress = currentDuration
                    tvElapsedTimeLabel?.text =
                        SongData.toMin(currentDuration.toLong(), requireContext())
                    tvRemainingTime?.text = SongData.toMin(
                        (songs[position].duration - currentDuration).toLong(),
                        requireContext()
                    )
                }
                if (this@MusicPlayerFragment.position > position) {
                    position = this@MusicPlayerFragment.position
                    setData(requireContext())
                }
                handler.postDelayed(this, 100)
            }
        }
        handler.post(runnable)
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(musicService)
        val notification = notification?.createNotification(songs[position], isPlaying)
        musicService.startForeground(1, notification)
        isLooping = musicService.isLoop()
        isShuffle = musicService.isShuffle()
    }

    private fun onLoop() {
        if (!isLooping) {
            imgReplayMusicPlayer.isSelected = true
            isLooping = true
            musicService.loopMusic(isLooping)
        } else {
            imgReplayMusicPlayer.isSelected = false
            isLooping = false
            musicService.loopMusic(isLooping)
        }
    }

    private fun onShuffle() {
        if (isShuffle) {
            imgShuffleMusicPlayer.isSelected = false
            isShuffle = false
            songs.clear()
            songs.addAll(SongData.getSong(requireContext()))
        } else {
            imgShuffleMusicPlayer.isSelected = true
            isShuffle = true
            songs.shuffle()
        }
    }
}
