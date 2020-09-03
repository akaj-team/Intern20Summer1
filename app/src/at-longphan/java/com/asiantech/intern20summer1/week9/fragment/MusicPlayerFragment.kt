package com.asiantech.intern20summer1.week9.fragment

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.Notification
import com.asiantech.intern20summer1.week9.model.Song
import com.asiantech.intern20summer1.week9.model.Units
import com.asiantech.intern20summer1.week9.service.PlayMusicService
import com.asiantech.intern20summer1.week9.service.PlayMusicService.Companion.isRepeat
import com.asiantech.intern20summer1.week9.service.PlayMusicService.Companion.isShuffle
import kotlinx.android.synthetic.`at-longphan`.fragment_music_player_w9.*

class MusicPlayerFragment : Fragment() {

    companion object {
        const val DELAY_TIME: Long = 0
        const val LIST_SONG_KEY = "list_song_key"
        const val IS_PLAYING_KEY = "is_playing_key"
        fun newInstance(songs: ArrayList<Song>, isPlaying: Boolean) =
            MusicPlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_SONG_KEY, songs)
                    putBoolean(IS_PLAYING_KEY, isPlaying)
                }
            }
    }

    private var songs: ArrayList<Song> = ArrayList()
    private var mPosition = 0
    private var notification: Notification? = null
    private val mHandler = Handler()
    private var mBounded: Boolean = false
    private var isPlaying = false
    private var playMusicService = PlayMusicService()

    private var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            playMusicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {

            mBounded = true
            val mLocalBinder = service as? PlayMusicService.LocalBinder
            mLocalBinder?.let { playMusicService = it.getServerInstance }
            mPosition = playMusicService.initPosition()
            isPlaying = playMusicService.isPlaying()
            initView()

            handleOnNotificationTogglePlay()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music_player_w9, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            songs = it.getParcelableArrayList<Song>(LIST_SONG_KEY)!!
            isPlaying = it.getBoolean(IS_PLAYING_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImageListeners()
        handleSeekBarMusicPlaying()
        initButtonPlayAndPauseView()


    }

    override fun onStart() {
        super.onStart()
        val mIntent = Intent(requireContext(), PlayMusicService::class.java)
        context?.bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBounded) {
            context?.unbindService(mConnection)
            mBounded = false
        }
    }

    @SuppressLint("ResourceType")
    private fun initView() {
        imgFragmentMainPlayerMusicBackground?.setImageURI(Uri.parse(songs[mPosition].image))
        imgDiskPlayer?.setImageURI(Uri.parse(songs[mPosition].image))
        tvMusicNameMainPlaying?.text = songs[mPosition].name
        tvMusicArtistMainPlaying?.text = songs[mPosition].artist

        if (isRepeat) {
            imgRepeat?.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
        } else {
            imgRepeat?.setColorFilter(Color.GRAY)
        }

        if (isShuffle) {
            imgShuffle?.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
        } else {
            imgShuffle?.setColorFilter(Color.GRAY)
        }

        val rotation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        imgDiskPlayer?.startAnimation(rotation)
        Toast.makeText(context, "lol", Toast.LENGTH_LONG).show()
        isPlaying = playMusicService.isPlaying()
        initButtonPlayAndPauseView()
    }

    private fun initButtonPlayAndPauseView() {
        if (isPlaying) {
            imgPlayAndPauseMain?.setImageResource(R.drawable.ic_pause)
        } else {
            imgPlayAndPauseMain?.setImageResource(R.drawable.ic_play)
        }
    }

    private fun initImageListeners() {
        imgPlayAndPauseMain?.setOnClickListener {
            togglePlayAndPause()
        }
        imgNextMain?.setOnClickListener {
            playNext()
        }
        imgPreviousMain?.setOnClickListener {
            playPrev()
        }
        imgShuffle?.setOnClickListener {
            toggleShuffle()
        }
        imgRepeat?.setOnClickListener {
            toggleRepeat()
        }
    }

    private fun playNext() {
        isPlaying = true
        playMusicService.playNext()
        initView()
    }

    private fun playPrev() {
        isPlaying = true
        playMusicService.playPrev()
        initView()
    }

    private fun togglePlayAndPause() {
        isPlaying = if (isPlaying) {
            imgPlayAndPauseMain?.setImageResource(R.drawable.ic_play)
            false
        } else {
            imgPlayAndPauseMain?.setImageResource(R.drawable.ic_pause)
            true
        }
        playMusicService.togglePlayAndPause()
    }

    @SuppressLint("ResourceType")
    private fun toggleShuffle() {
        isShuffle = if (!isShuffle) {
            imgShuffle?.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
            true
        } else {
            imgShuffle?.setColorFilter(Color.GRAY)
            false
        }
    }

    @SuppressLint("ResourceType")
    private fun toggleRepeat() {
        isRepeat = if (!isRepeat) {
            imgRepeat?.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
            true
        } else {
            imgRepeat?.setColorFilter(Color.GRAY)
            false
        }
    }

    private fun handleSeekBarMusicPlaying() {
        seekBarMusicPlaying?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTimeStart?.text = Units.convertTimeMusic(seekBarMusicPlaying.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playMusicService.seekTo(seekBarMusicPlaying.progress)
            }
        })

        var position = mPosition
        val runnable = object : Runnable {
            override fun run() {
                seekBarMusicPlaying?.max = songs[mPosition].duration
                mPosition = playMusicService.initPosition()
                val currentPosition = playMusicService.currentPosition()
                if (currentPosition != null) {
                    seekBarMusicPlaying?.progress = currentPosition
                    tvTimeStart?.text = Units.convertTimeMusic(seekBarMusicPlaying.progress)
                    tvTimeEnd?.text = Units.convertTimeMusic(songs[mPosition].duration)
                }
                if (mPosition > position) {
                    try {
                        position = mPosition
                        initView()
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                }
                mHandler.postDelayed(this, DELAY_TIME)
            }
        }
        mHandler.post(runnable)
    }

    private fun createNotification(position: Int) {
        notification = Notification(playMusicService)
        val notification =
            notification?.createPlayMusicNotification(songs[position], isPlaying)
        playMusicService.startForeground(1, notification)
        isPlaying = playMusicService.isPlaying()
        isRepeat = playMusicService.isRepeat()
        isShuffle = playMusicService.isShuffle()
    }

    private fun handleOnNotificationTogglePlay(){
        playMusicService.onNotificationChange = {
            isPlaying = playMusicService.isPlaying()
            initButtonPlayAndPauseView()
        }
    }
}
