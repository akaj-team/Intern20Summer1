package com.asiantech.intern20summer1.week9.fragments

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.adapters.SongViewHolder
import com.asiantech.intern20summer1.week9.models.Song
import kotlinx.android.synthetic.`at-linhle`.fragment_list_song.*


class ListSongFragment : Fragment() {
    companion object {
        private const val REQUEST_CODE_READ = 100
        fun newInstance() = ListSongFragment()
    }

    private var position: Int = 0
    private lateinit var songList: ArrayList<Song>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runTimePermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ) {
                runTimePermission()
            }
        }
    }

    private fun getSongFromDevice() {
        songList = arrayListOf()
        val adapter = SongViewHolder(songList)
        val contentResolver = context?.contentResolver
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
                songList.add(
                    Song(
                        currentTitle,
                        currentArtist,
                        currentDuration,
                        imgUri.toString(),
                        currentId,
                        Uri.withAppendedPath(uri, cursor.getInt(id).toString()).toString()
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }
        recyclerViewSongContainer.layoutManager = LinearLayoutManager(activity)
        setOnclick(adapter, songList)
        recyclerViewSongContainer.adapter = adapter
    }

    private fun runTimePermission() {
        val permission = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        } else {
            getSongFromDevice()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_READ
        )
    }

    private fun setOnclick(adapter: SongViewHolder, songList: ArrayList<Song>) {
        adapter.onClicked = {
            position = it
            fragmentManager?.beginTransaction()?.replace(
                R.id.flMusicContainer,
                PlaySongFragment.newInstance(songList, position)
            )?.addToBackStack(null)?.commit()
        }
    }
}
