package com.asiantech.intern20summer1.w9.fragments

import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import com.asiantech.intern20summer1.w9.managers.SongRecyclerAdapter
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.services.BackgroundSoundService
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_music.*
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class MusicFragment : Fragment() {

    companion object {
        internal fun newInstance() = MusicFragment()
    }

    private val songLists = mutableListOf<Song>()
    private val songAdapter = SongRecyclerAdapter(songLists)
    private val service = MusicActivity.service

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSongData()
        initSongAdapter()
        initView()
        onServiceListener()
        initButtonListener()
    }

    private fun initView() {
        initPlayerBar(service)
        tvNameSongPlayerBar.isSelected = true
    }

    private fun initButtonListener() {
        imgPlayer?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onPauseMusic()
            } else {
                service.onResumeMusic()
            }
        }
        imgLeftNext?.setOnClickListener {
            service.onNextLeftMusic()
        }
        imgRightNext?.setOnClickListener {
            service.onNextRightMusic()
        }
    }

    private fun initSongAdapter() {
        recyclerViewSongList?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        songAdapter.onItemClick = { position ->
            service.onStartMusic(position)
        }
    }

    private fun initSongData() {
        scanSongInStore().apply {
            toCollection(songLists)
            toCollection(service.songList)
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
        d("XXX", sv.songPlaying.toString())
        sv.songPlaying?.let {
            if (sv.audioPlayer.isPlaying) {
                imgPlayer?.setImageResource(R.drawable.ic_pause_button)
                val bitmap = Song().getPicture(requireContext(), it)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                imgDvdPlayerBar?.startAnim()
            } else {
                imgPlayer?.setImageResource(R.drawable.ic_play_button)
                val bitmap = Song().getPicture(requireContext(), it)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                imgDvdPlayerBar?.endAnim()
            }
        }
    }

    private fun onServiceListener() {
        service.onStartPlayerBar = {
            service.songPlaying?.let { song ->
                val bitmap = Song().getPicture(requireContext(), song)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                imgPlayer?.setImageResource(R.drawable.ic_pause_button)
                imgDvdPlayerBar?.startAnim()
                tvNameSongPlayerBar?.text = song.nameSong
            }
        }
        service.onPausePlayerBar = {
            imgDvdPlayerBar?.pauseAnim()
            imgPlayer?.setImageResource(R.drawable.ic_play_button)
        }
        service.onResumePlayerBar = {
            imgDvdPlayerBar?.resumeAnim()
            imgPlayer?.setImageResource(R.drawable.ic_pause_button)
        }
    }
}
