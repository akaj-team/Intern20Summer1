package com.asiantech.intern20summer1.week9

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
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.fragment_play_song.*

class PlaySongFragment : Fragment() {
    companion object {
        private const val DELAY_TIME = 500L
        private const val LIST_SONG_KEY = "list"
        private const val IS_SONG_PLAY_KEY = "isPlay"
        fun newInstance(songList: ArrayList<Song>, isPlay: Boolean?) =
            PlaySongFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_SONG_KEY, songList)
                    putBoolean(IS_SONG_PLAY_KEY, isPlay!!)
                }
            }
    }

    private var songList: ArrayList<Song> = arrayListOf()
    private var musicService = MusicService()
    private var isPlaying: Boolean? = false
    private var isRepeat: Boolean = false
    private var isShuffle: Boolean = false
    private var position = 0
    private var createNotification: CreateNotification? = null
    private var bounded = false
    private var run = true
    private var handler = Handler()
    private var musicConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            bounded = false
            musicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bounded = true
            val localBinder = service as MusicService.LocalBinder
            musicService = localBinder.getServerInstance
            position = musicService.currentPosition()
            isPlaying = musicService.isPlaying()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            songList = it.getParcelableArrayList<Song>(LIST_SONG_KEY) as ArrayList<Song>
            isPlaying = it.getBoolean(IS_SONG_PLAY_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onPlayPause()
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
        run = false
        if (bounded) {
            context?.unbindService(musicConnection)
            bounded = false
        }
    }

    private fun createNotification(position: Int) {
        createNotification = CreateNotification(musicService)
        val notification = createNotification?.createNotification(songList[position], isPlaying)
        musicService.startForeground(1, notification)
        isPlaying = musicService.isPlaying()
        isRepeat = musicService.isRepeat()
        isShuffle = musicService.isShuffle()
    }

    private fun getDuration(duration: Int): String {
        val second = duration / 1000 % 60
        val minute = ((duration - second) / 1000 / 60).toLong()
        return String.format("%02d: %02d", minute, second)
    }

    private fun handleSeekBar() {
        var seekPosition = position
        val runnable = object : Runnable {
            override fun run() {
                if (run) {
                    seekBar?.max = songList[position].duration
                    position = musicService.currentPosition()
                    val seekBarPosition = musicService.seekBarPositon()
                    if (seekBarPosition != null) {
                        seekBar?.progress = seekBarPosition
                        tvStartTime?.text = seekBar?.progress?.let { getDuration(it) }
                        tvEndTime?.text = getDuration(songList[position].duration)
                    }
                    if (position > seekPosition) {
                        seekPosition = position
                        initView(requireContext())
                    } else {
                        initView(requireContext())
                    }
                    handler.postDelayed(this, DELAY_TIME)
                }
            }
        }
        handler.post(runnable)
    }

    private fun initView(context: Context) {
        val song = songList[musicService.currentPosition()]
        tvSongName?.text = song.name
        tvArtist?.text = song.artist
        tvEndTime.text = getDuration(song.duration)
        val bitmap = context.let { Utils.convertToBitmap(it, Uri.parse(song.image)) }
        if (bitmap == null) {
            circleImageViewLogo?.setImageResource(R.drawable.ic_launcher_background)
        } else {
            circleImageViewLogo?.setImageBitmap(bitmap)
        }
        if (isRepeat) {
            imgRepeat.setColorFilter(Color.MAGENTA)
        } else {
            imgRepeat.setColorFilter(Color.GRAY)
        }
        if (isShuffle) {
            imgShuffle.setColorFilter(Color.MAGENTA)
        } else {
            imgShuffle.setColorFilter(Color.GRAY)
        }
        onPlayPause()
    }

    private fun control() {
        imgPlay.setOnClickListener {
            initPlayPauseMedia()
            createNotification(position)
        }
        imgNexSong.setOnClickListener {
            onNext()
        }
        imgPreviousSong.setOnClickListener {
            onPrevious()
        }
        imgRepeat.setOnClickListener {
            onRepeat()
        }
        imgShuffle.setOnClickListener {
            onShuffle()
        }
    }

    private fun onPlayPause() {
        if (musicService.isPlaying()!!) {
            imgPlay?.setImageResource(R.drawable.pause)
        } else {
            imgPlay?.setImageResource(R.drawable.play)
        }
    }

    private fun onNext() {
        if (position == songList.size - 1) position = 0
        position++
        createNotification(position)
        musicService.nextSong()
        isPlaying = true
        initView(requireContext())
    }

    private fun onPrevious() {
        if (position == 0) position = songList.size - 1
        position--
        createNotification(position)
        musicService.previousSong()
        isPlaying = true
        initView(requireContext())
    }

    private fun onRepeat() {
        isRepeat = if (isRepeat) {
            imgRepeat.setColorFilter(Color.GRAY)
            false
        } else {
            imgRepeat.setColorFilter(Color.MAGENTA)
            true
        }
    }


    private fun onShuffle() {
        isShuffle = if (isShuffle) {
            imgRepeat.setColorFilter(Color.GRAY)
            false
        } else {
            imgRepeat.setColorFilter(Color.MAGENTA)
            true
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
        handleSeekBar()
    }

    private fun initPlayPauseMedia() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlay.setImageResource(R.drawable.play)
                true
            }
            else -> {
                imgPlay.setImageResource(R.drawable.pause)
                false
            }
        }
        musicService.playPause()
    }
}
