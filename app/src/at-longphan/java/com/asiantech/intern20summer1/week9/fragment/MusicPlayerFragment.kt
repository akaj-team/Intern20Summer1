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
import com.asiantech.intern20summer1.week9.service.PlayMusicService.Companion.isReNew
import com.asiantech.intern20summer1.week9.service.PlayMusicService.Companion.isShuffle
import kotlinx.android.synthetic.`at-longphan`.fragment_music_player_w9.*


class MusicPlayerFragment : Fragment() {

    private var listMainMusic: ArrayList<Song> = ArrayList()
    private var mPosition = 0
    private var notification: Notification? = null
    private val mHandler = Handler()
    private var mBounded: Boolean = false
    private var isPlaying = false
    private var playMusicService = PlayMusicService()

    companion object {
        const val DELAY_TIME: Long = 1000
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music_player_w9, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listMainMusic = it.getParcelableArrayList<Song>(LIST_SONG_KEY)!!
            isPlaying = it.getBoolean(IS_PLAYING_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        updateMusic()
        initPlayPauseButton()
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
        imgFragmentMainPlayerMusicBackground.let {
            it.setImageURI(Uri.parse(listMainMusic[mPosition].image))
            it.background.alpha = 0
        }
        imgDiskPlayer.setImageURI(Uri.parse(listMainMusic[mPosition].image))
        tvMusicNameMainPlaying.text = listMainMusic[mPosition].name
        tvMusicArtistMainPlaying.text = listMainMusic[mPosition].artist

        if (isReNew) {
            imgRenew.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
        } else {
            imgRenew.setColorFilter(Color.GRAY)
        }
        if (isShuffle) {
            imgShuffle.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
        }
        val rotation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        imgDiskPlayer.startAnimation(rotation)
    }

    private fun initPlayPauseButton() {
        if (isPlaying) {
            imgPlayAndPauseMain.setImageResource(R.drawable.ic_pause)
        } else {
            imgPlayAndPauseMain.setImageResource(R.drawable.ic_play)
        }
    }

    private fun initListener() {
        imgPlayAndPauseMain.setOnClickListener {
            initPlayPauseMedia()
        }
        imgNextMain.setOnClickListener {
            nextMusic()
        }
        imgPreviousMain.setOnClickListener {
            previousMusic()
        }
        imgShuffle.setOnClickListener {
            initShuffle()
        }
        imgRenew.setOnClickListener {
            initRenew()
        }
    }

    private fun nextMusic() {
        isPlaying = true
        mPosition++
        if (mPosition > listMainMusic.size - 1) mPosition = 0
        playMusicService.initNextMusic()
        createNotification(mPosition)
        initView()
    }

    private fun previousMusic() {
        isPlaying = true
        mPosition--
        if (mPosition < 0) mPosition = listMainMusic.size - 1
        playMusicService.initPreviousMusic()
        createNotification(mPosition)
        initView()
    }

    private fun initPlayPauseMedia() {
        isPlaying = if (isPlaying) {
            imgPlayAndPauseMain.setImageResource(R.drawable.ic_play)
            false
        } else {
            imgPlayAndPauseMain.setImageResource(R.drawable.ic_pause)
            true
        }
        playMusicService.initPlayPause()
    }

    @SuppressLint("ResourceType")
    private fun initShuffle() {
        if (!isShuffle) {
            imgShuffle.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
            isShuffle = true
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_shuffle_enable),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
            isShuffle = false
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_shuffle_disable),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("ResourceType")
    private fun initRenew() {
        if (!isReNew) {
            imgRenew.setColorFilter(Color.parseColor(resources.getString(R.color.colorImageViewShuffleEnableWeek9Background)))
            isReNew = true
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_replay_enable),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            imgRenew.setColorFilter(Color.GRAY)
            isReNew = false
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_replay_disable),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateMusic() {
        seekBarMusicPlaying.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                seekBarMusicPlaying?.max = listMainMusic[mPosition].duration
                mPosition = playMusicService.initPosition()
                val currentPosition = playMusicService.currentPosition()
                if (currentPosition != null) {
                    seekBarMusicPlaying?.progress = currentPosition
                    tvTimeStart?.text = Units.convertTimeMusic(seekBarMusicPlaying.progress)
                    tvTimeEnd?.text = Units.convertTimeMusic(listMainMusic[mPosition].duration)
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
        }
    }

    private fun createNotification(position: Int) {
        notification = Notification(playMusicService)
        val notification = notification?.createNotification(listMainMusic[position], isPlaying)
        playMusicService.startForeground(1, notification)
        isPlaying = playMusicService.isPlaying()
        isReNew = playMusicService.isRenew()
        isShuffle = playMusicService.isShulle()
    }
}
