package com.asiantech.intern20summer1.week9.fragment

import android.Manifest
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.MainActivityWeekNine
import com.asiantech.intern20summer1.week9.adapter.SongAdapter
import com.asiantech.intern20summer1.week9.model.Song
import com.asiantech.intern20summer1.week9.model.Units
import com.asiantech.intern20summer1.week9.service.PlayMusicService
import kotlinx.android.synthetic.`at-longphan`.fragment_list_song_w9.*

class SongListFragment : Fragment() {

    companion object {
        private const val READ_AND_WRITE_REQUEST_CODE = 3
    }

    private var songs: ArrayList<Song> = arrayListOf()
    private var adapter = SongAdapter(songs)
    private var mPosition: Int = 0
    private var mBounded: Boolean = false
    private var isPlaying = false
    private var playMusicService = PlayMusicService()

    private var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            playMusicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBounded = true
            val mLocalBinder = service as PlayMusicService.LocalBinder
            playMusicService = mLocalBinder.getServerInstance
            mPosition = playMusicService.currentPos
            isPlaying = playMusicService.isMediaPlayerPlaying()

            initPlayMusicBarView()
            handleOnNotificationListener()
        }
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
        initListeners()
        if (checkReadAndWritePermissions()) {
            initData()
        } else {
            requestReadAndWritePermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_AND_WRITE_REQUEST_CODE) {
            if (checkReadAndWritePermissions()) {
                initData()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.toast_read_and_write_permission_denied),
                    Toast.LENGTH_SHORT
                ).show()
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

    private fun checkReadAndWritePermissions(): Boolean {
        val permissionRead = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val permissionWrite = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissionRead == PackageManager.PERMISSION_GRANTED
                && permissionWrite == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadAndWritePermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), READ_AND_WRITE_REQUEST_CODE
        )
    }

    internal fun initPlayMusicBarView() {
        imgImagePlaying?.setImageURI(Uri.parse(songs[mPosition].image))
        tvTitlePlaying?.text = songs[mPosition].name
        initButtonPlayAndPause()
    }

    private fun initView() {
        recyclerViewListSong?.layoutManager = LinearLayoutManager(requireContext())

        if (!isMyServiceRunning(PlayMusicService::class.java)) {
            cardViewPlayMusicBar?.visibility = View.GONE
        } else {
            cardViewPlayMusicBar?.visibility = View.VISIBLE
        }
    }

    private fun initData() {
        songs.clear()
        songs.addAll(Units.insertData(requireContext()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initAdapter() {
        recyclerViewListSong.adapter = adapter
        adapter.onItemClicked = {
            cardViewPlayMusicBar?.visibility = View.VISIBLE
            mPosition = it

            val musicDataIntent = Intent(requireContext(), PlayMusicService::class.java)
            musicDataIntent.putParcelableArrayListExtra(SongAdapter.SONG_LIST_PATH, songs)
            musicDataIntent.putExtra(SongAdapter.SONG_ITEM_POSITION, mPosition)
            requireContext().startForegroundService(musicDataIntent)
            isPlaying = true
            initPlayMusicBarView()
        }
    }

    private fun initListeners() {
        imgPlayAndPause?.setOnClickListener {
            togglePlayAndPause()
        }
        imgNext?.setOnClickListener {
            playNext()
        }
        imgPrevious?.setOnClickListener {
            playPrev()
        }
        cardViewPlayMusicBar?.setOnClickListener {
            replaceMusicPlayerFragment()
        }
    }

    private fun replaceMusicPlayerFragment() {
        (activity as MainActivityWeekNine)
            .replaceFragment(
                MusicPlayerFragment
                    .newInstance(songs, isPlaying)
                , true
            )
    }

    private fun initButtonPlayAndPause() {
        if (isPlaying) {
            imgPlayAndPause?.setImageResource(R.drawable.ic_pause)
        } else {
            imgPlayAndPause?.setImageResource(R.drawable.ic_play)
        }
    }

    private fun togglePlayAndPause() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlayAndPause?.setImageResource(R.drawable.ic_play)
                false
            }
            else -> {
                imgPlayAndPause?.setImageResource(R.drawable.ic_pause)
                true
            }
        }
        playMusicService.togglePlayAndPause()
    }

    private fun playNext() {
        isPlaying = true
        playMusicService.playNext()
        mPosition = playMusicService.currentPos
        initPlayMusicBarView()
    }

    private fun playPrev() {
        isPlaying = true
        playMusicService.playPrev()
        mPosition = playMusicService.currentPos
        initPlayMusicBarView()
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = context?.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun handleOnNotificationListener() {
        playMusicService.onMusicNotificationSelected = {
            mPosition = playMusicService.currentPos
            isPlaying = playMusicService.isPlaying
            initPlayMusicBarView()
        }
    }
}
