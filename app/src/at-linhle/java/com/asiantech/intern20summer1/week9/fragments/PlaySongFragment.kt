package com.asiantech.intern20summer1.week9.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder.Companion.ONE_THOUSAND
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder.Companion.SIXTY
import com.asiantech.intern20summer1.week9.extensions.CreateNotification
import com.asiantech.intern20summer1.week9.extensions.Utils
import com.asiantech.intern20summer1.week9.models.Song
import com.asiantech.intern20summer1.week9.services.MusicService
import com.asiantech.intern20summer1.week9.services.MusicService.Companion.isRePeat
import com.asiantech.intern20summer1.week9.services.MusicService.Companion.isShuffle
import kotlinx.android.synthetic.`at-linhle`.fragment_play_song.*

class PlaySongFragment : Fragment() {

    companion object {
        private const val DELAY_TIME = 500L
        private const val LIST_SONG_KEY = "list"
        private const val IS_SONG_PLAY_KEY = "isPlay"
        fun newInstance(songList: ArrayList<Song>, isPlay: Boolean) =
            PlaySongFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_SONG_KEY, songList)
                    putBoolean(IS_SONG_PLAY_KEY, isPlay)
                }
            }
    }

    private var musicService = MusicService()
    private var songList: ArrayList<Song> = ArrayList()
    private var position = 0
    private var isPlaying = false
    private var createNotification: CreateNotification? = null
    private var bounded: Boolean = false

    @Suppress("DEPRECATION")
    private var handler = Handler()

    private var musicConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            bounded = false
            musicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            bounded = true
            val localBinder = service as MusicService.LocalBinder
            musicService = localBinder.getServerInstance
            position = musicService.initPosition()
            isPlaying = musicService.isPlaying()
            initView(requireContext())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            songList = it.getParcelableArrayList<Song>(LIST_SONG_KEY)!!
            isPlaying = it.getBoolean(IS_SONG_PLAY_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_song, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayPauseButton()
        updateUI()
        control()
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), MusicService::class.java)
        context?.bindService(intent, musicConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (bounded) {
            context?.unbindService(musicConnection)
            bounded = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun control() {
        imgPlay.setOnClickListener {
            initPlayPauseMedia()
            createNotification(position)
        }
        imgNexSong.setOnClickListener {
            onNextSong()
        }
        imgPreviousSong.setOnClickListener {
            onPreviousSong()
        }
        imgShuffle.setOnClickListener {
            onShuffle()
        }
        imgRepeat.setOnClickListener {
            onRepeat()
        }
    }

    private fun initView(context: Context) {
        val song = songList[musicService.initPosition()]
        tvSongName?.text = song.songName
        tvArtist?.text = song.artist
        tvEndTime?.text = getDuration(song.duration)
        val bitmap = context.let { Utils.convertToBitmap(it, Uri.parse(song.imgUri)) }
        if (bitmap == null) {
            circleImageViewLogo?.setImageResource(R.drawable.logo)
        } else {
            circleImageViewLogo?.setImageBitmap(bitmap)
        }
        if (isRePeat) {
            imgRepeat.setColorFilter(Color.MAGENTA)
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
        }
        if (isShuffle) {
            imgShuffle.setColorFilter(Color.MAGENTA)
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
        }
        initPlayPauseButton()
    }

    private fun initPlayPauseButton() {
        if (!musicService.isPlaying()) {
            imgPlay?.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        } else {
            imgPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
        }
    }

    private fun onNextSong() {
        position++
        if (position > songList.size - 1) position = 0
        musicService.initNextMusic()
        createNotification(position)
        isPlaying = true
        initView(requireContext())
    }

    private fun onPreviousSong() {
        position--
        if (position < 0) position = songList.size - 1
        musicService.initPreviousMusic()
        createNotification(position)
        isPlaying = true
        initView(requireContext())
    }

    private fun initPlayPauseMedia() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                true
            }
            else -> {
                imgPlay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                false
            }
        }
        musicService.initPlayPause()
    }

    private fun onShuffle() {
        if (!isShuffle) {
            imgShuffle.setColorFilter(Color.MAGENTA)
            isShuffle = true
            Toast.makeText(
                requireContext(),
                getString(R.string.shuffle_enabled_description),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
            isShuffle = false
            Toast.makeText(
                requireContext(),
                getString(R.string.shuffle_disabled_description),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onRepeat() {
        if (!isRePeat) {
            imgRepeat.setColorFilter(Color.MAGENTA)
            isRePeat = true
            Toast.makeText(
                requireContext(),
                getString(R.string.repeat_enabled_description),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            imgRepeat.setColorFilter(Color.GRAY)
            isRePeat = false
            Toast.makeText(
                requireContext(),
                getString(R.string.repeat_disabled_description),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateUI() {
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvStartTime?.text = seekBar?.progress?.let { getDuration(it) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let { musicService.seekTo(it) }
            }
        })
        handleSeekBarChange()
    }

    private fun handleSeekBarChange() {
        var position = this.position
        val runnable = object : Runnable {
            override fun run() {
                seekBar?.max = songList[this@PlaySongFragment.position].duration
                this@PlaySongFragment.position = musicService.initPosition()
                val currentPosition = musicService.currentPosition()
                if (currentPosition != null) {
                    seekBar?.progress = currentPosition
                    tvStartTime?.text = seekBar?.progress?.let { getDuration(it) }
                    tvEndTime?.text = getDuration(songList[this@PlaySongFragment.position].duration)
                }
                if (this@PlaySongFragment.position > position) {
                    position = this@PlaySongFragment.position
                    initView(requireContext())
                } else {
                    initView(requireContext())
                }
                handler.postDelayed(this, DELAY_TIME)
            }
        }
        handler.post(runnable)
    }

    private fun createNotification(position: Int) {
        createNotification = CreateNotification(musicService)
        val notification = createNotification?.createNotification(songList[position], isPlaying)
        musicService.startForeground(1, notification)
        isPlaying = musicService.isPlaying()
        isRePeat = musicService.isRepeat()
        isShuffle = musicService.isShuffle()
    }

    private fun getDuration(duration: Int): String {
        val seconds = duration / ONE_THOUSAND % SIXTY
        val minutes = ((duration - seconds) / ONE_THOUSAND / SIXTY).toLong()
        return String.format("%02d: %02d", minutes, seconds)
    }
}
