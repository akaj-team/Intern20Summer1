package com.asiantech.intern20summer1.fragment.w9

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.w9.MusicAdapter
import com.asiantech.intern20summer1.data.w9.Music
import com.asiantech.intern20summer1.data.w9.MusicAction
import com.asiantech.intern20summer1.data.w9.MusicData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-vuongphan`.w9_fragment_list_music.*

class ListFragmentMusic : Fragment(), View.OnClickListener {
    private val music = mutableListOf<Music>()
    private var adapterRecycler = MusicAdapter(music)
    private var positionMusicPlaying = DEFAULT_VALUE
    private var musicService = MusicService()
    private var musicBound = false
    private var isPlaying = false

    companion object {
        internal fun newInstance(): ListFragmentMusic {
            return ListFragmentMusic()
        }

        private const val PERMISSION_CODE = 101
        private const val DEFAULT_VALUE = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_list_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        initAdapter()
        initData()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(context, MusicService::class.java)
        context?.bindService(intent, musicConnection, Context.BIND_AUTO_CREATE)
        positionMusicPlaying = musicService.getPosition()
        btnPlayPause.isSelected = musicService.isPlaying()
        setStatus()
    }

    override fun onClick(view: View?) {
        when (view) {
            btnPlayPause -> {
                onPausePlayMusic()
            }
        }
    }

    private var musicConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as? MusicService.LocalBinder
            musicService = binder?.getService!!
            positionMusicPlaying = musicService.getPosition()
            btnPlayPause.isSelected = musicService.isPlaying()
            setStatus()
            musicBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            musicBound = false
        }
    }

    private fun playMusic(position: Int) {
        MusicService.startService(requireContext(), position)
        setStatus()
        btnPlayPause?.isSelected = true
        isPlaying = true
    }

    private fun onPausePlayMusic() {
        isPlaying = if (!isPlaying) {
            sendAction(MusicAction.PLAY)
            btnPlayPause.isSelected = true
            btnPlayPause.setBackgroundResource(R.drawable.ic_pause_circle_outline_red)
            true
        } else {
            btnPlayPause.isSelected = false
            sendAction(MusicAction.PAUSE)
            btnPlayPause.setBackgroundResource(R.drawable.ic_play_circle_outline_red)
            false
        }
    }

    private fun sendAction(action: String) {
        val intent = Intent(requireContext(), MusicService::class.java)
        intent.action = action
        intent.putExtra(action, "1")
        context?.startService(intent)
    }

    private fun setStatus() {
        if (positionMusicPlaying > music.size - 1 || positionMusicPlaying < 0) {
            positionMusicPlaying = 0
            Glide.with(requireContext())
                .load(music[positionMusicPlaying].image)
                .placeholder(R.drawable.ic_background_auto_play)
                .into(imgMusicBottom)
            tvNameMusicBottom.text = music[positionMusicPlaying].name
            tvArtistBottom.text = music[positionMusicPlaying].artist
        } else {
            Glide.with(requireContext())
                .load(music[positionMusicPlaying].image)
                .placeholder(R.drawable.ic_background_auto_play)
                .into(imgMusicBottom)
            tvNameMusicBottom.text = music[positionMusicPlaying].name
            tvArtistBottom.text = music[positionMusicPlaying].artist
        }

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let {
                    context?.let { context ->
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )
                    }
                }
                == PackageManager.PERMISSION_DENIED ||
                this.let {
                    context?.let { context ->
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
                == PackageManager.PERMISSION_DENIED) {
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            }
        }
    }

    private fun initAdapter() {
        rvMusic.adapter = adapterRecycler
        rvMusic.layoutManager = LinearLayoutManager(context)
        rvMusic.setHasFixedSize(false)
    }

    private fun initListeners() {
        adapterRecycler.onSongClicked = {
            Toast.makeText(context, music[it].name, Toast.LENGTH_SHORT).show()
            positionMusicPlaying = it
            btnPlayPause.setBackgroundResource(R.drawable.ic_pause_circle_outline_red)
            playMusic(it)
        }
        btnPlayPause?.setOnClickListener(this)
    }

    private fun initData() {
        rvMusic.clearOnScrollListeners()
        music.clear()
        music.addAll(MusicData.getMusic(requireContext()))
    }
}
