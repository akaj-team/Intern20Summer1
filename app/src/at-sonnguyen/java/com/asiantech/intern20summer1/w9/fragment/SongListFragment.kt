package com.asiantech.intern20summer1.w9.fragment

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
import com.asiantech.intern20summer1.w9.data.MusicAction
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData
import com.asiantech.intern20summer1.w9.notification.CreateNotification
import kotlinx.android.synthetic.`at-sonnguyen`.w9_fragment_song_list.*

class SongListFragment : Fragment() {
    companion object {
        private const val PERMISSION_CODE = 1001
        private const val DEFAULT_VALUE = 0
        internal fun instance() = SongListFragment()
    }

    private var positionSongPlaying = DEFAULT_VALUE
    private var musicBound = false
    private var isPlaying = false
    private var isLooping = false
    private var isShuffle = false
    private var notification: CreateNotification? = null
    private var musicService = ForegroundService()
    private var songs = mutableListOf<Song>()
    private lateinit var songAdapter: SongAdapter

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
        initListener()
    }

    override fun onStart() {
        super.onStart()
//        val intent = Intent(context, ForegroundService::class.java)
////        context?.bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE)
////        positionSongPlaying = musicService.getPosition()
////        imgPlaySongList.isSelected = musicService.isPlaying()
////        setCardViewData()
    }

    private var musicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as ForegroundService.LocalBinder
            musicService = binder.getService
            positionSongPlaying = musicService.getPosition()
            imgPlaySongList.isSelected = musicService.isPlaying()
            setCardViewData()
            musicBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            musicService.stopSelf()
            musicBound = false
        }

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
            positionSongPlaying = it
            setCardViewData()
            playMusic(it)
        }
    }

    private fun onPauseOrPlayMusic() {
        isPlaying = if (!isPlaying) {
            sendAction(MusicAction.PLAY)
            imgPlaySongList.isSelected = true
            true
        } else {
            imgPlaySongList.isSelected = false
            sendAction(MusicAction.PAUSE)
            false
        }
    }

    private fun handlePlayImageViewListener() {
        imgPlaySongList.setOnClickListener {
            onPauseOrPlayMusic()
        }
    }

    private fun handleNextImageViewListener() {
        imgNextSongList.setOnClickListener {
            sendAction(MusicAction.NEXT)
            positionSongPlaying++
            setCardViewData()
        }
    }

    private fun handleSongImageViewListener() {
        imgSmallSong.setOnClickListener {
            (activity as? MusicPlayerActivity)?.replaceFragment(
                MusicPlayerFragment.instance(
                    positionSongPlaying
                )
            )
        }
    }

    private fun playMusic(position: Int) {
        ForegroundService.startService(requireContext(), position)
        setCardViewData()
        imgPlaySongList.isSelected = true
        isPlaying = true
    }

//    private fun onPauseOrPlayMusic() {
//        isPlaying = if (!isPlaying) {
//            sendAction(MusicAction.PLAY)
//            imgPlaySongList.isSelected = true
//            true
//        } else {
//            imgPlaySongList.isSelected = false
//            sendAction(MusicAction.PAUSE)
//            false
//        }
//    }

    private fun sendAction(action: String) {
        val intent = Intent(requireContext(), ForegroundService::class.java)
        intent.action = action
        intent.putExtra(action, getString(R.string.w9_put_extra_action_value))
        context?.startService(intent)
    }

    private fun setCardViewData() {
        var bitmap = SongData.convertUriToBitmap(songs[positionSongPlaying].uri,requireContext())
        if (bitmap == null){
            imgSmallSong.setImageResource(R.drawable.ic_song)
        }else{
            imgSmallSong.setImageBitmap(bitmap)
        }
        tvCardViewSongName.text = songs[positionSongPlaying].songName
    }
}
