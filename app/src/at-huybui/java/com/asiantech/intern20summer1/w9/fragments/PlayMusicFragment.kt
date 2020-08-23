package com.asiantech.intern20summer1.w9.fragments

import android.animation.ObjectAnimator
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import com.asiantech.intern20summer1.w9.managers.SongRecyclerAdapter
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.services.BackgroundSoundService
import com.asiantech.intern20summer1.w9.services.BackgroundSoundService.LocalBinder
import com.asiantech.intern20summer1.w9.utils.Music
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_play_music.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class PlayMusicFragment : Fragment() {

    companion object {
        internal fun newInstance() = PlayMusicFragment()
    }

    private var animRotate = ObjectAnimator()

    private val songLists = mutableListOf<Song>()
    private val songAdapter = SongRecyclerAdapter(songLists)
    private var service = BackgroundSoundService()
    private var svc = Intent()
    var bound = false

    private var connection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            bound = false
        }

        override fun onServiceConnected(component: ComponentName?, iBinder: IBinder?) {
            val binder = iBinder as LocalBinder
            service = binder.getService()
            initPlayerBar(service)
            bound = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        svc = Intent(requireContext(), service::class.java)
        (activity as MusicActivity).bindService(svc, connection, Context.BIND_AUTO_CREATE)
        return inflater.inflate(R.layout.w9_fragment_play_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAnim()
        initSongData()
        initSongAdapter()
        initView()
    }

    override fun onStop() {
        super.onStop()
        (activity as MusicActivity).unbindService(connection)
        bound = false
    }

    private fun initView() {
        imgPlayer?.setOnClickListener {
            if (service.player.isPlaying) {
                imgPlayer.setImageResource(R.drawable.ic_play_button)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    animRotate.pause()
                    service.onPauseMusic()
                }
            } else {
                imgPlayer.setImageResource(R.drawable.ic_pause_button)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    animRotate.resume()
                    service.onStartMusic()
                }
            }
        }
    }

    private fun initSongAdapter() {
        recyclerViewSongList?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        songAdapter.onItemClick = { position ->
            songLists[position].let { song ->
                val bitmap = Music().getPicture(requireContext(), song)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                animRotate.start()
                (activity as MusicActivity).apply {
                    bindService(intent, connection, Context.BIND_AUTO_CREATE);
                    svc.putExtra("song", song as Serializable)
                    startService(svc)
                }
            }
        }
    }

    private fun initSongData() {
        scanSongInStore().apply {
            toCollection(songLists)
            songAdapter.notifyDataSetChanged()
        }
    }

    private fun scanSongInStore(): MutableList<Song> {
        val songListsNew = mutableListOf<Song>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME
        )
        val selection = "${MediaStore.Audio.Media.DATE_ADDED} >= ?"
        val selectionArgs = arrayOf(
            // Release day of the G1. :)
            dateToTimestamp(day = 22, month = 10, year = 2000).toString()
        )
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        requireContext().applicationContext.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            d("XXX", "Found ${cursor.count} audio")
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                )

                val song = Song(id = id, contentUri = contentUri.toString())
                songListsNew.add(song)
            }
        }
        return songListsNew
    }

    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }

    private fun initPlayerBar(sv: BackgroundSoundService) {
        sv.song?.let {
            if (sv.player.isPlaying) {
                imgPlayer?.setImageResource(R.drawable.ic_pause_button)
                val bitmap = Music().getPicture(requireContext(), it)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                animRotate.start()
            } else {
                imgPlayer?.setImageResource(R.drawable.ic_play_button)
                val bitmap = Music().getPicture(requireContext(), it)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                animRotate.end()

            }
        }
    }

    private fun createAnim() {
        animRotate = ObjectAnimator.ofFloat(imgDvdPlayerBar, "rotation", 0.0F, 360.0F)
        animRotate.duration = 20000L
        animRotate.repeatCount = ObjectAnimator.INFINITE
        animRotate.repeatMode = ObjectAnimator.RESTART
    }
}
