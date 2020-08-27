package com.asiantech.intern20summer1.week9.fragments

import android.Manifest
import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
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
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder
import com.asiantech.intern20summer1.week9.extensions.CreateNotification
import com.asiantech.intern20summer1.week9.extensions.Utils
import com.asiantech.intern20summer1.week9.models.Song
import com.asiantech.intern20summer1.week9.services.MusicService
import com.asiantech.intern20summer1.week9.services.MusicService.Companion.LIST_SONG_KEY
import com.asiantech.intern20summer1.week9.services.MusicService.Companion.SONG_POSITION_KEY
import com.asiantech.intern20summer1.week9.views.MusicMediaActivity
import kotlinx.android.synthetic.`at-linhle`.fragment_list_song.*


class ListSongFragment : Fragment() {
    companion object {
        private const val DELAY_TIME = 500L
        private const val REQUEST_CODE_READ = 100
        fun newInstance() = ListSongFragment()
    }

    private var listMusic: ArrayList<Song> = arrayListOf()
    private var listPath: ArrayList<String> = ArrayList()
    private var adapter = SongViewHolder(listMusic)
    private var position: Int = 0
    private var notification: CreateNotification? = null
    private var bounded: Boolean = false
    private var isPlaying = false
    private var musicService = MusicService()
    private var isServiceConnected = false

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
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_song, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleRenderUI()
        runTimePermission()
        initView()
        initAdapter()
        control()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fragmentManager?.beginTransaction()?.replace(
                    R.id.flMusicContainer,
                    newInstance()
                )?.commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), MusicService::class.java)
        context?.bindService(intent, musicConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (bounded) {
            context?.unbindService(musicConnection)
            bounded = false
        }
    }

    private fun initView() {
        recyclerViewSongContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initData() {
        listMusic.clear()
        listMusic.apply {
            addAll(Utils.getSongFromDevice(requireContext()))
        }
    }

    private fun isPermission() =
        PackageManager.PERMISSION_GRANTED == context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun runTimePermission() {
        if (!isPermission()) {
            makeRequest()
        } else {
            initData()
            context?.apply {
                initViewToBottomMedia()
            }
        }
    }

    private fun makeRequest() {
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initAdapter() {
        recyclerViewSongContainer.adapter = adapter
        adapter.onClicked = {
            isServiceConnected = true
            position = it
            val musicDataIntent = Intent(requireContext(), MusicService::class.java)
            musicDataIntent.putStringArrayListExtra(LIST_SONG_KEY, listPath)
            musicDataIntent.putParcelableArrayListExtra(LIST_SONG_KEY, listMusic)
            musicDataIntent.putExtra(SONG_POSITION_KEY, position)
            requireContext().startForegroundService(musicDataIntent)
            isPlaying = true
            initViewToBottomMedia()
        }
    }

    private fun initViewToBottomMedia() {
        val song = listMusic[musicService.initPosition()]
        tvMusicName?.text = song.songName
        tvSingerName?.text = song.artist
        val bitmap = context?.let { Utils.convertToBitmap(it, Uri.parse(song.imgUri)) }
        if (bitmap != null) {
            imgMusicArt?.setImageBitmap(bitmap)
        } else {
            imgMusicArt?.setImageResource(R.drawable.logo)
        }
        initPlayPauseButton()
    }

    private fun initPlayPauseButton() {
        if (!musicService.isPlaying()) {
            imgPlayPause?.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        } else {
            imgPlayPause?.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
        }
    }

    private fun initPlayPauseMedia() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                true
            }
            else -> {
                imgPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                false
            }
        }
        musicService.initPlayPause()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onNextSong() {
        musicService.initNextMusic()
        position = musicService.initPosition()
        createNotification(position)
        isPlaying = true
        initViewToBottomMedia()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onPreviousSong() {
        musicService.initPreviousMusic()
        position = musicService.initPosition()
        createNotification(position)
        isPlaying = true
        initViewToBottomMedia()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun control() {
        imgPlayPause.setOnClickListener {
            if (!isServiceConnected) {
                Toast.makeText(
                    activity,
                    getString(R.string.is_service_connected_description),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                initPlayPauseMedia()
                createNotification(position)
            }
        }
        imgNext.setOnClickListener {
            onNextSong()
        }
        imgPrevious.setOnClickListener {
            onPreviousSong()
        }
        clBottomMedia.setOnClickListener {
            if (!isServiceConnected) {
                Toast.makeText(
                    activity,
                    getString(R.string.is_service_connected_description),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                initMainMediaPage()
            }
        }
    }

    private fun initMainMediaPage() {
        (activity as MusicMediaActivity)
            .replaceFragment(PlaySongFragment.newInstance(listMusic, isPlaying), true)
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(musicService)
        val notification = notification?.createNotification(listMusic[position], isPlaying)
        musicService.startForeground(1, notification)
        isPlaying = musicService.isPlaying()
    }

    private fun handleRenderUI() {
        var uiPosition = position
        val runnable = object : Runnable {
            override fun run() {
                if (position > uiPosition) {
                    uiPosition = position
                    initViewToBottomMedia()
                } else {
                    initViewToBottomMedia()
                }
                handler.postDelayed(this, DELAY_TIME)
            }
        }
        handler.post(runnable)
    }
}
