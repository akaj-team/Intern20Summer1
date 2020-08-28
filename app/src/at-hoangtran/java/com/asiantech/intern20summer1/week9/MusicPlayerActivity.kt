package com.asiantech.intern20summer1.week9

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_music_player.*
import kotlinx.android.synthetic.`at-hoangtran`.song_item_recycler.*

class MusicPlayerActivity : AppCompatActivity() {

    companion object {
        const val READ_REQUEST_CODE = 1
        const val DELAY_TIME = 500L
        const val LIST_KEY = "list"
        const val PLAY_KEY = "isPlay"
    }

    private var musicService = MusicService()
    private var positon = 0
    private var isPlaying = false
    private var musicNotification: MusicNotification? = null
    private var bounded: Boolean = false
    private var totalTime: Int = 0
    private lateinit var mp: MediaPlayer
    private lateinit var songList: ArrayList<Song>
    private val songAdapter = MusicAdapter(songList)


    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            bounded = false
            musicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bounded = true
            val songBinder = service as MusicService.SongBinder
            musicService = songBinder.getService()
            positon = musicService.getPosition()
            isPlaying = musicService.isPlaying()
            initView(this@MusicPlayerActivity)
        }

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        handleProgressBar()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleFunctions() {
        btnSongPlayerPlayPause.setOnClickListener {
            onPlayPause()
            createNotification(positon)
        }
        btnSongPlayerNextSong.setOnClickListener {
            onNext()
        }
        btnSongPlayerPreviousSong.setOnClickListener {
            onPrevious()
        }
    }

    private fun handleProgressBar() {
        seekBarSongPlayer.max = totalTime
        seekBarSongPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun handleThread() {
        val handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val currentPosition = msg.what
                seekBarSongPlayer.progress = currentPosition
                val elapsedTime = createTimeLabel(currentPosition)
                tvElapsedTime.text = elapsedTime
                val remainingTime = createTimeLabel(totalTime - currentPosition)
                tvRemainingTime.text = "-$remainingTime"
            }
        }

        Thread(Runnable {
            while (true) {
                try {
                    val msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()


    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel: String
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getSongFromDevice() {
        songList = arrayListOf()
        val adapter = MusicAdapter(songList)
        val contentResolver = contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val author = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            do {
                val currentId = cursor.getLong(id)
                val imgUri = ContentUris.withAppendedId(uri, currentId)
                val currentTitle = cursor.getString(title)
                val currentArtist = cursor.getString(author)
                val currentDuration = cursor.getInt(duration)
                songList.add(
                    Song(
                        currentDuration, currentTitle, currentArtist, imgUri.toString(), currentId
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }
        songRecyclerView.layoutManager = LinearLayoutManager(this)
        handleButton()
        songRecyclerView.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun runTimePermission() {
        val permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        } else {
            getSongFromDevice()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_REQUEST_CODE
        )
    }

    private fun initView(context: Context) {
        val song = songList[musicService.getPosition()]
        tvSongPlayerSongName.text = song.name
        tvSongPlayerSongAuthor.text = song.author
        tvSongDuration.text = getDuration(song.duration)
        val bitmap = convertToBitmap(Uri.parse(song.imgUri))
        if (bitmap == null) {
            imgSongPlayer.setImageResource(R.mipmap.ic_launcher_round)
        } else {
            imgSongPlayer.setImageBitmap(bitmap)
        }
        onPlayPause()
    }

    private fun onPlayPause() {
        if (isPlaying) {
            btnSongPlayerPlayPause.setBackgroundResource(R.drawable.play)
        } else {
            btnSongPlayerPlayPause.setBackgroundResource(R.drawable.pause)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onNext() {
        positon++
        if (positon > songList.size - 1) positon = 0
        musicService.nextSong()
        createNotification(positon)
        isPlaying = true
        initView(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onPrevious() {
        positon--
        if (positon < 0) positon = songList.size - 1
        musicService.previousSong()
        createNotification(positon)
        isPlaying = true
        initView(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNotification(positon: Int) {
        musicNotification = MusicNotification(musicService)
        val notification = musicNotification?.createNotification(songList[positon], isPlaying)
        musicService.startForeground(1, notification)
        isPlaying = musicService.isPlaying()
    }

    private fun getDuration(duration: Int): String {
        val sec = duration / 1000 * 60
        val min = ((duration - sec) / 1000 / 60).toLong()
        return String.format("%02d:%02d", min, sec)
    }

    private fun convertToBitmap(path: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }
}
