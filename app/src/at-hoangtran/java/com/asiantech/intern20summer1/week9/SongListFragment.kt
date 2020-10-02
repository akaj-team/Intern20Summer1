package com.asiantech.intern20summer1.week9

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.MusicService.Companion.SONG_LIST_KEY
import com.asiantech.intern20summer1.week9.MusicService.Companion.SONG_POSITION_KEY
import kotlinx.android.synthetic.`at-hoangtran`.fragment_play_song.*
import kotlinx.android.synthetic.`at-hoangtran`.fragment_song_list.*
import java.util.Collections.addAll

class SongListFragment : Fragment() {
    companion object {
        private const val DELAY_TIME = 500L
        private const val REQUEST_CODE_READ = 100
    }

    private var songList: ArrayList<Song> = arrayListOf()
    private var pathList: ArrayList<String> = arrayListOf()
    private var adapter = SongViewHolder(songList)
    private var position: Int = 0
    private var createNotification: CreateNotification? = null
    private var bounded: Boolean = false
    private var isPlaying: Boolean? = false
    private var musicService = MusicService()
    private var isServiceConnected = false
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runTimePermission()
        renderUI()
        initView()
        initAdapter()
        control()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fragmentManager?.beginTransaction()?.replace(R.id.flMusicMedia, SongListFragment())
                    ?.commit()
            }
        }
    }

    private fun isPermission() = PackageManager.PERMISSION_GRANTED == context?.let {
        ContextCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun runTimePermission() {
        if (isPermission()) {
            initData()
            context?.apply {
                initView()
            }
        } else {
            makeRequest()
        }
    }

    private fun makeRequest() {
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ)
    }

    override fun onStart() {
        super.onStart()
        if (bounded) {
            context?.unbindService(musicConnection)
            bounded = false
        }
    }

    override fun onStop() {
        super.onStop()
        if (bounded) {
            context?.unbindService(musicConnection)
            bounded = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initAdapter() {
        recyclerViewSongContainer.adapter = adapter
        recyclerViewSongContainer.layoutManager = LinearLayoutManager(requireContext())
        adapter.onClicked = {
            isServiceConnected = true
            position = it
            val musicDataIntent = Intent(requireContext(), MusicService::class.java)
            musicDataIntent.putStringArrayListExtra(SONG_LIST_KEY, pathList)
            musicDataIntent.putParcelableArrayListExtra(SONG_LIST_KEY, songList)
            musicDataIntent.putExtra(SONG_POSITION_KEY, position)
            requireContext().startForegroundService(musicDataIntent)
            isPlaying = true
            initView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initData() {
        songList.clear()
            .apply {
                addAll(Utils.getSongFromDevice(requireContext()))
            }
    }

    private fun createNotification(position: Int) {
        createNotification = CreateNotification(musicService)
        val notification = createNotification?.createNotification(songList[position], isPlaying)
        musicService.startForeground(1, notification)
        isPlaying = musicService.isPlaying()
    }

    private fun initView() {
        val song = songList[musicService.currentPosition()]
        tvMusicName?.text = song.name
        tvArtist?.text = song.artist
        val bitmap = Utils.convertToBitmap(requireContext(), Uri.parse(song.image))
        if (bitmap != null) {
            imgMusicArt.setImageBitmap(bitmap)
        } else {
            imgMusicArt.setImageResource(R.drawable.ic_launcher_background)
        }
        handlePlayPauseButton()
    }

    private fun handlePlayPauseButton() {
        if (musicService.isPlaying()!!) {
            imgPlayPause.setImageResource(R.drawable.pause)
        } else {
            imgPlayPause.setImageResource(R.drawable.play)
        }
    }

    private fun handlePlayPauseMedia() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlayPause.setImageResource(R.drawable.play)
                true
            }
            else -> {
                imgPlayPause.setImageResource(R.drawable.pause)
                false
            }
        }
        musicService.playPause()
    }

    private fun onNextSong() {
        musicService.nextSong()
        position = musicService.currentPosition()
        createNotification(position)
        isPlaying = true
        initView()
    }

    private fun onPreviousSong() {
        musicService.previousSong()
        position = musicService.currentPosition()
        createNotification(position)
        isPlaying = true
        initView()
    }

    private fun control() {
        imgPlayPause.setOnClickListener {
            if (isServiceConnected) {
                handlePlayPauseMedia()
                createNotification(position)
            } else {
                Toast.makeText(context, "Pick a song to play", Toast.LENGTH_SHORT).show()
            }
        }
        imgNext.setOnClickListener {
            onNextSong()
        }
        imgPrevious.setOnClickListener {
            onPreviousSong()
        }
        clBottomMedia.setOnClickListener {
            if (isServiceConnected) {
                replacePlaySongFragment()
            } else {
                Toast.makeText(context, "Pick a song to play", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun replacePlaySongFragment() {
        (activity as MusicMediaActivity).replaceFragment(
            PlaySongFragment.newInstance(
                songList,
                isPlaying)
            , true
        )
    }

    private fun renderUI() {
        var uiPosition = position
        val runnable = object : Runnable {
            override fun run() {
                if (position > uiPosition) {
                    uiPosition = position
                    initView()
                } else {
                    initView()
                }
                handler.postDelayed(this, DELAY_TIME)
            }
        }
        handler.post(runnable)
    }
}
