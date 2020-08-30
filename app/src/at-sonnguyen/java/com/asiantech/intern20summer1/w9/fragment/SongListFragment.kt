package com.asiantech.intern20summer1.w9.fragment

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.ForegroundService
import com.asiantech.intern20summer1.w9.activity.MusicPlayerActivity
import com.asiantech.intern20summer1.w9.adapter.SongAdapter
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData
import com.asiantech.intern20summer1.w9.notification.CreateNotification
import kotlinx.android.synthetic.`at-sonnguyen`.w9_fragment_song_list.*

@Suppress("DEPRECATION")
class SongListFragment : Fragment() {
    companion object {
        private const val PERMISSION_CODE = 1001
        private const val DEFAULT_VALUE = 0
        internal const val DELAY_TIME = 100
        internal fun instance() = SongListFragment()
    }

    private var positionSongPlaying = DEFAULT_VALUE
    private var isBound = false
    private var flag = false
    private var isPlayingSongList = false
    private var notification: CreateNotification? = null
    private var musicService = ForegroundService()
    private var songs = mutableListOf<Song>()
    private lateinit var songAdapter: SongAdapter
    private var handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        initAdapter()
        initData()
        refreshCardView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(context, ForegroundService::class.java)
        context?.bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE)
        positionSongPlaying = musicService.getPosition()
        isPlayingSongList = musicService.isPlaying()
        imgPlaySongList.isSelected = musicService.isPlaying()
        setCardViewData()
    }

    private var musicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as ForegroundService.LocalBinder
            musicService = binder.getService
            positionSongPlaying = musicService.getPosition()
            imgPlaySongList.isSelected = musicService.isPlaying()
            setCardViewData()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            musicService.stopSelf()
            isBound = false
        }
    }

    override fun onStop() {
        super.onStop()
        isBound = false
    }

    private fun initAdapter() {
        songAdapter = SongAdapter(songs)
        recyclerViewSongList.adapter = songAdapter
    }

    private fun initData() {
        songs.clear()
        songs.addAll(SongData.getSong(requireContext()))
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let {
                    context?.let { context ->
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    }
                } == PackageManager.PERMISSION_DENIED || this.let {
                    context?.let { context ->
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                } == PackageManager.PERMISSION_DENIED) {
                val permissions =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
        }
    }

    private fun initListener() {
        handleItemClickListener()
        handlePlayImageViewListener()
        handleNextImageViewListener()
        handleSongImageViewListener()
    }

    private fun handleItemClickListener() {
        songAdapter.onItemClicked = {
            imgPlaySongList.isSelected = isPlayingSongList
            flag = true
            positionSongPlaying = it
            setCardViewData()
            playMusic(it)
            createNotification(it)
            isPlayingSongList = true
        }
    }

    private fun onPauseOrPlayMusic() {
        musicService.onPauseOrPlay()
        isPlayingSongList = !isPlayingSongList
        imgPlaySongList.isSelected = !imgPlaySongList.isSelected
        createNotification(positionSongPlaying)
    }

    private fun handlePlayImageViewListener() {
        imgPlaySongList.setOnClickListener {
            if (flag) {
                onPauseOrPlayMusic()
            } else {
                playMusic(positionSongPlaying)
                isPlayingSongList = true
                musicService.isPlaying = true
                flag = true
                imgPlaySongList.isSelected = true
                createNotification(positionSongPlaying)
            }
        }
    }

    private fun handleNextImageViewListener() {
        imgNextSongList.setOnClickListener {
            musicService.isPlaying =true
            isPlayingSongList =true
            musicService.playNext(positionSongPlaying)
            positionSongPlaying++
            if (positionSongPlaying > songs.size - 1) {
                positionSongPlaying = 0
            }
            flag = true
            setCardViewData()
            createNotification(positionSongPlaying)
        }
    }

    private fun handleSongImageViewListener() {
        imgSmallSong.setOnClickListener {
            (activity as? MusicPlayerActivity)?.replaceFragment(
                MusicPlayerFragment.instance(positionSongPlaying, isPlayingSongList)
            )
        }
    }

    private fun playMusic(position: Int) {
        ForegroundService.startService(requireContext(), position)
        setCardViewData()
        isPlayingSongList = true
        musicService.isPlaying =true
        imgPlaySongList.isSelected = isPlayingSongList
    }


    private fun setCardViewData() {
        val bitmap = SongData.convertUriToBitmap(songs[positionSongPlaying].uri, requireContext())
        if (bitmap == null) {
            imgSmallSong?.setImageResource(R.drawable.ic_song)
        } else {
            imgSmallSong?.setImageBitmap(bitmap)
        }
        tvCardViewSongName?.text = songs[positionSongPlaying].songName
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(musicService)
        val notification = notification?.createNotification(songs[position], isPlayingSongList)
        musicService.startForeground(1, notification)
        isPlayingSongList = musicService.isPlaying()
    }

    private fun refreshCardView() {
        var currentPosition = positionSongPlaying
        val runnable = object : Runnable {
            override fun run() {
                if (positionSongPlaying != currentPosition) {
                    currentPosition = positionSongPlaying
                    setCardViewData()
                } else {
                    setCardViewData()
                }
                handler.postDelayed(this, DELAY_TIME.toLong())
            }
        }
        handler.post(runnable)
    }
}
