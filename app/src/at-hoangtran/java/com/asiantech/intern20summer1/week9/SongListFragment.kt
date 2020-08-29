package com.asiantech.intern20summer1.week9

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.MusicPlayerActivity.Companion.LIST_KEY
import com.asiantech.intern20summer1.week9.MusicService.Companion.POSITION_KEY
import kotlinx.android.synthetic.`at-hoangtran`.activity_music_player.*
import kotlinx.android.synthetic.`at-hoangtran`.list_song_recycler.*
import kotlinx.android.synthetic.`at-hoangtran`.song_item_recycler.*

class SongListFragment : Fragment() {
    companion object {
        private const val DELAY_TIME = 500L
        private const val REQUEST_CODE_READ = 100
        fun newInstance() = SongListFragment()
    }

    private var listMusic: ArrayList<Song> = arrayListOf()
    private var listPath: ArrayList<String> = ArrayList()
    private var adapter = MusicAdapter(listMusic)
    private var position: Int = 0
    private var notification: MusicNotification? = null
    private var bounded: Boolean = false
    private var isPlaying = false
    private var musicService = MusicService()
    private var isServiceConnected = false
    private lateinit var songList: ArrayList<Song>

    @Suppress("DEPRECATION")
    private var handler = Handler()

    private var musicConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            bounded = false
            musicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            bounded = true
            val songBinder = service as MusicService.SongBinder
            musicService = songBinder.getService()
            position = musicService.getPosition()
            isPlaying = musicService.isPlaying()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_song_recycler, container, false)
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
                    R.id.flContainer,
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

    private fun initView() {
        rvSongList.layoutManager = LinearLayoutManager(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initData() {
        listMusic.clear()
        listMusic.apply {
            addAll(getSongFromDevice(requireContext()))
        }
    }

    @SuppressLint("InlinedApi")
    private fun getSongFromDevice(context: Context): ArrayList<Song> {
        listMusic.clear()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver?.query(uri, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            do {
                val currentId = cursor.getLong(id)
                val imgUri = ContentUris.withAppendedId(uri, currentId)
                val currentTitle = cursor.getString(title)
                val currentArtist = cursor.getString(artist)
                val currentDuration = cursor.getInt(duration)
                listMusic.add(
                    Song(
                        currentDuration,
                        currentTitle,
                        currentArtist,
                        imgUri.toString(),
                        currentId,
                        Uri.withAppendedPath(uri, cursor.getInt(id).toString()).toString()
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }
        return listMusic
    }


    private fun isPermission() =
        PackageManager.PERMISSION_GRANTED == context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

    @SuppressLint("NewApi")
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
        rvSongList.adapter = adapter
        adapter.onClicked = {
            isServiceConnected = true
            position = it
            val musicDataIntent = Intent(requireContext(), MusicService::class.java)
            musicDataIntent.putStringArrayListExtra(LIST_KEY, listPath)
            musicDataIntent.putParcelableArrayListExtra(LIST_KEY, listMusic)
            musicDataIntent.putExtra(POSITION_KEY, position)
            requireContext().startForegroundService(musicDataIntent)
            isPlaying = true
            initViewToBottomMedia()
        }
    }

    private fun initViewToBottomMedia() {
        val song = listMusic[musicService.getPosition()]
        tvSongName?.text = song.name
        tvSongAuthor?.text = song.author
        val bitmap = context?.let { convertToBitmap(it, Uri.parse(song.imgUri)) }
        if (bitmap != null) {
            imgPlayPause?.setImageBitmap(bitmap)
        } else {
            imgPlayPause?.setImageResource(R.mipmap.ic_launcher_round)
        }
        initPlayPauseButton()
    }

    private fun convertToBitmap(context: Context, path: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(activity, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    private fun initPlayPauseButton() {
        if (!musicService.isPlaying()) {
            imgPlayPause?.setImageResource(R.drawable.play)
        } else {
            imgPlayPause?.setImageResource(R.drawable.pause)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initPlayPauseMedia() {
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
        musicService.playSong()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onNextSong() {
        musicService.nextSong()
        position = musicService.getPosition()
        createNotification(position)
        isPlaying = true
        initViewToBottomMedia()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onPreviousSong() {
        musicService.previousSong()
        position = musicService.getPosition()
        createNotification(position)
        isPlaying = true
        initViewToBottomMedia()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun control() {
        btnSongPlayerPlayPause.setOnClickListener {
            if (!isServiceConnected) {
                Toast.makeText(
                    activity,
                    "select a song to play",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                initPlayPauseMedia()
                createNotification(position)
            }
        }
        btnSongPlayerNextSong.setOnClickListener {
            onNextSong()
        }
        btnSongPlayerPreviousSong.setOnClickListener {
            onPreviousSong()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNotification(position: Int) {
        notification = MusicNotification(musicService)
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
