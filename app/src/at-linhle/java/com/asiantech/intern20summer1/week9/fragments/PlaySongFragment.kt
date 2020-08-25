package com.asiantech.intern20summer1.week9.fragments

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder.Companion.ONE_THOUSAND
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder.Companion.SIXTY
import com.asiantech.intern20summer1.week9.extensions.CreateNotification
import com.asiantech.intern20summer1.week9.extensions.Utils
import com.asiantech.intern20summer1.week9.models.Song
import com.asiantech.intern20summer1.week9.services.MusicService
import kotlinx.android.synthetic.`at-linhle`.fragment_play_song.*

class PlaySongFragment : Fragment() {

    companion object {
        private const val OBJECT_ANIMATOR_DURATION = 6000L
        private const val FLOAT_VALUE = 360F
        private const val LIST_SONG_KEY = "list"
        private const val SONG_POSITION_KEY = "position"
        fun newInstance(songList: ArrayList<Song>, position: Int) =
            PlaySongFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_SONG_KEY, songList)
                    putInt(SONG_POSITION_KEY, position)
                }
            }
    }

    private lateinit var musicService: MusicService
    private lateinit var rotate: ObjectAnimator
    private lateinit var songList: ArrayList<Song>
    private var position = 0
    private var intent: Intent? = null
    private var isPlay = false
    private var createNotification: CreateNotification? = null

    @Suppress("DEPRECATION")
    private var handler = Handler()

    private val musicConnection: ServiceConnection = object : ServiceConnection {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("ClickableViewAccessibility")
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val songBinder: MusicService.SongBinder = service as MusicService.SongBinder
            musicService = songBinder.getService()
            seekBar?.setOnTouchListener { p0, p1 ->
                if (p1?.action == MotionEvent.ACTION_MOVE) {
                    if (p0?.id == R.id.seekBar) {
                        musicService.seekTo(seekBar.progress)
                    }
                }
                true
            }
            updateUI()
            createNotification(position)
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getSongData()
        activity?.startService(context?.let { MusicService.newInstance(it, songList, position) })
        return inflater.inflate(R.layout.fragment_play_song, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply {
            initView(this)
            val intent = Intent(this, MusicService::class.java)
            activity?.bindService(intent, musicConnection, Service.BIND_AUTO_CREATE)
        }
        control()
    }

    override fun onStart() {
        super.onStart()
        if (intent == null) {
            intent = Intent(context, MusicService::class.java)
            context?.bindService(intent, musicConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.stopService(intent)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun control() {
        imgPlay.setOnClickListener {
            if (!isPlay) {
                musicService.pause()
                imgPlay?.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                isPlay = true
                rotate.pause()
            } else {
                musicService.start()
                imgPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                isPlay = false
                rotate.resume()
            }
            createNotification(position)
        }

        imgNexSong.setOnClickListener {
            onNextSong()
        }

        imgPreviousSong.setOnClickListener {
            onPreviousSong()
        }
    }

    private fun getSongData() {
        arguments?.apply {
            songList = getParcelableArrayList<Song>(LIST_SONG_KEY) as ArrayList<Song>
            position = getInt(SONG_POSITION_KEY)
        }
    }

    private fun initView(context: Context) {
        val song = songList[position]

        tvSongName?.text = song.songName
        tvArtist?.text = song.artist
        tvEndTime?.text = getDuration(song.duration)
        val bitmap = context.let { Utils.convertToBitmap(it, Uri.parse(song.imgUri)) }
        if (bitmap == null) {
            circleImageViewLogo?.setImageResource(R.drawable.logo)
        } else {
            circleImageViewLogo?.setImageBitmap(bitmap)
        }
        val service = MusicService.newInstance(context, songList, position)
        context.startService(service)
        rotate = ObjectAnimator.ofFloat(
            circleImageViewLogo,
            getString(R.string.play_song_fragment_property_description),
            0F,
            FLOAT_VALUE
        ).apply {
            duration = OBJECT_ANIMATOR_DURATION
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun setSongData() {
        position = musicService.getPosition()
        val model = songList[position]
        tvSongName?.text = model.songName
        tvArtist?.text = model.artist
        val duration = model.duration
        tvEndTime?.text = getDuration(duration)
        val bitmap = context?.let { Utils.convertToBitmap(it, Uri.parse(model.imgUri)) }
        if (bitmap == null) {
            circleImageViewLogo?.setImageResource(R.drawable.logo)
        } else {
            circleImageViewLogo?.setImageBitmap(bitmap)
        }
    }

    fun updateUI() {
        activity?.runOnUiThread(object : Runnable {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun run() {
                seekBar?.max = songList[position].duration
                val progress = musicService.getCurrentPosition()
                seekBar?.progress = progress
                tvStartTime.text = getDuration(musicService.getCurrentPosition())
                if (progress > seekBar?.max?.minus(0) ?: 0) {
                    onNextSong()
                }
                handler.postDelayed(this, 333)
            }
        })
    }

    private fun createNotification(position: Int) {
        createNotification = CreateNotification(requireContext())
        val notification = createNotification?.createNotificationMusic(songList[position], isPlay)
        musicService.startForeground(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onNextSong() {
        if (!isPlay) {
            musicService.nextSong()
            setSongData()
        } else {
            musicService.nextSong()
            setSongData()
            imgPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            rotate.resume()
            isPlay = false
        }
        createNotification(position)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onPreviousSong() {
        if (!isPlay) {
            musicService.previousSong()
            setSongData()
        } else {
            musicService.previousSong()
            setSongData()
            imgPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            rotate.resume()
            isPlay = false
        }
        createNotification(position)
    }

    private fun getDuration(duration: Int): String {
        val seconds = duration / ONE_THOUSAND % SIXTY
        val minutes = ((duration - seconds) / ONE_THOUSAND / SIXTY).toLong()
        return String.format("%02d: %02d", minutes, seconds)
    }
}
