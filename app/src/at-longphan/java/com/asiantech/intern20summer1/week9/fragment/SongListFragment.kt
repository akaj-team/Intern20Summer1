package com.asiantech.intern20summer1.week9.fragment

import android.Manifest
import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.MainActivityWeek9
import com.asiantech.intern20summer1.week9.Notification
import com.asiantech.intern20summer1.week9.adapter.SongAdapter
import com.asiantech.intern20summer1.week9.model.Song
import com.asiantech.intern20summer1.week9.model.Units
import com.asiantech.intern20summer1.week9.service.PlayMusicService
import kotlinx.android.synthetic.`at-longphan`.fragment_list_song_w9.*

class SongListFragment : Fragment() {

    private var songs: ArrayList<Song> = arrayListOf()
    private var listPath: ArrayList<String> = ArrayList()
    private var adapter = SongAdapter(songs)
    private var mPosition: Int = 0
    private var notification: Notification? = null
    private var mBounded: Boolean = false
    private var isPlaying = false
    private var playMusicService = PlayMusicService()

    companion object {
        private const val REQUEST_CODE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_song_w9, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAdapter()
        initListener()
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_CODE
            )
        } else initData()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Successes", Toast.LENGTH_LONG).show()
                initData()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val mIntent = Intent(requireContext(), PlayMusicService::class.java)
        context?.bindService(mIntent, mConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBounded) {
            context?.unbindService(mConnection)
            mBounded = false
        }
    }

    private fun initBottomView() {
        imgImagePlaying.setImageURI(Uri.parse(songs[mPosition].image))
        tvTitlePlaying.text = songs[mPosition].name
        initPlayPauseButton()
    }

    private fun initView() {
        recyclerViewListSong.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initData() {
        songs.clear()
        songs.apply {
            addAll(Units.insertData(requireContext()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initAdapter() {
        recyclerViewListSong.adapter = adapter
        adapter.onItemClicked = {
            mPosition = it
            Toast.makeText(
                requireContext(),
                "Playing " + songs[it].name,
                Toast.LENGTH_SHORT
            ).show()
            val musicDataIntent = Intent(requireContext(), PlayMusicService::class.java)
            musicDataIntent.putStringArrayListExtra(SongAdapter.SONG_LIST_PATH, listPath)
            musicDataIntent.putParcelableArrayListExtra(SongAdapter.SONG_LIST_PATH, songs)
            musicDataIntent.putExtra(SongAdapter.SONG_ITEM_POSITION, mPosition)
            requireContext().startForegroundService(musicDataIntent)
            isPlaying = true
            initBottomView()
        }
    }

    private fun initListener() {
        imgPlayAndPause.setOnClickListener {
            initPlayPauseMedia()
        }
        imgNext.setOnClickListener {
            nextMusic()
        }
        imgPrevious.setOnClickListener {
            previousMusic()
        }
        cardViewPlayMusic.setOnClickListener {
            initMainMediaPage()
        }
    }

    private fun initMainMediaPage() {
        (activity as MainActivityWeek9)
            .replaceFragment(
                MusicPlayerFragment
                    .newInstance(songs, isPlaying)
                , true
            )
    }

    private fun initPlayPauseButton() {
        if (isPlaying) {
            imgPlayAndPause.setImageResource(R.drawable.ic_pause)
        } else {
            imgPlayAndPause.setImageResource(R.drawable.ic_play)
        }
    }

    private fun initPlayPauseMedia() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlayAndPause.setImageResource(R.drawable.ic_play)
                false
            }
            else -> {
                imgPlayAndPause.setImageResource(R.drawable.ic_pause)
                true
            }
        }
        playMusicService.initPlayPause()
    }

    private fun nextMusic() {
        playMusicService.initNextMusic()
        mPosition = playMusicService.initPosition()
        createNotification(mPosition)
        initBottomView()
    }

    private fun previousMusic() {
        playMusicService.initPreviousMusic()
        mPosition = playMusicService.initPosition()
        createNotification(mPosition)
        initBottomView()
    }

    private var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            playMusicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBounded = true
            val mLocalBinder = service as PlayMusicService.LocalBinder
            playMusicService = mLocalBinder.getServerInstance
            mPosition = playMusicService.initPosition()
            isPlaying = playMusicService.isPlaying()
            initBottomView()
        }
    }

    private fun createNotification(position: Int) {
        notification = Notification(playMusicService)
        val notification = notification?.createNotification(songs[position], isPlaying)
        playMusicService.startForeground(1, notification)
        isPlaying = playMusicService.isPlaying()
    }
}
