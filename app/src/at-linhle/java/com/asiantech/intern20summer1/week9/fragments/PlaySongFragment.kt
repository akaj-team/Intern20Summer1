package com.asiantech.intern20summer1.week9.fragments

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder.Companion.ONE_THOUSAND
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder.Companion.SIXTY
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

    private lateinit var rotate: ObjectAnimator
    private lateinit var songList: ArrayList<Song>
    private var position = 0
    private var intent: Intent? = null
    private var musicService = MusicService.newInstance()
    private var musicBound = false
    private var isPlay = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_song, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSongData()
        control()
//        seekBar()

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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
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
        }

        imgNexSong.setOnClickListener {
            onNextSong()
        }

        imgPreviousSong.setOnClickListener {
            onPreviousSong()
        }
    }

    private var musicConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            musicBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder

            musicService = binder.getService
            musicService.setList(songList)
            musicService.setPosition(position)
            musicService.playSong()
            musicBound = true
        }
    }

    private fun getSongData() {
        songList = arguments?.getParcelableArrayList<Song>(LIST_SONG_KEY) as ArrayList<Song>
        position = arguments?.getInt(SONG_POSITION_KEY) as Int

        val song = songList[position]

        tvSongName?.text = song.songName
        tvArtist?.text = song.artist
        val bitmap = context?.let { Utils.convertToBitmap(it, Uri.parse(song.imgUri)) }
        if (bitmap == null) {
            circleImageViewLogo?.setImageResource(R.drawable.logo)
        } else {
            circleImageViewLogo?.setImageBitmap(bitmap)
        }
    }

    private fun setSongData() {
        val model = songList[musicService.getPosition()]
        tvSongName?.text = model.songName
        val duration = model.duration
        tvEndTime?.text = getDuration(duration)
        val bitmap = context?.let { Utils.convertToBitmap(it, Uri.parse(model.imgUri)) }
        if (bitmap == null) {
            circleImageViewLogo?.setImageResource(R.drawable.logo)
        } else {
            circleImageViewLogo?.setImageBitmap(bitmap)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun onNextSong() {
        if (!isPlay) {
            musicService.playNext()
            setSongData()
        } else {
            musicService.playNext()
            setSongData()
            imgPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            rotate.resume()
            isPlay = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun onPreviousSong() {
        if (!isPlay) {
            musicService.playPrev()
            setSongData()
        } else {
            musicService.playPrev()
            setSongData()
            imgPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            rotate.resume()
            isPlay = false
        }
    }

    private fun getDuration(duration: Int): String {
        val seconds = duration / ONE_THOUSAND % SIXTY
        val minutes = ((duration - seconds) / ONE_THOUSAND / SIXTY).toLong()
        return String.format("%02d: %02d", minutes, seconds)
    }

}
