package com.asiantech.intern20summer1.w9.fragments

import android.content.ContentUris
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.managers.SongRecyclerAdapter
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.utils.Music
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_play_music.*
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

    var mediaPlayer: MediaPlayer? = null
    private val songLists = mutableListOf<Song>()
    private val songAdapter = SongRecyclerAdapter(songLists)
    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_play_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSongData()
        initSongAdapter()
        initView()
    }

    var count = 0
    private fun initView() {
    }

    private fun initSongAdapter() {
        recyclerViewSongList?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        songAdapter.onItemClick = { posision ->
            songLists[posision].let { song ->
                val bitmap = Music().getPicture(requireContext(), song)
                imgDvdPlayerBar?.setImageBitmap(bitmap)
                val animRotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
                imgDvdPlayerBar?.startAnimation(animRotate)
            }
            mediaPlayer?.stop()
            mediaPlayer =
                MediaPlayer.create(requireContext(), Uri.parse(songLists[posision].contentUri))
            mediaPlayer?.start()
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
}
