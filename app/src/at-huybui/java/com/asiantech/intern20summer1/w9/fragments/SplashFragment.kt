package com.asiantech.intern20summer1.w9.fragments

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import com.asiantech.intern20summer1.w9.models.Song
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class SplashFragment : Fragment() {

    companion object {
        internal fun newInstance() = SplashFragment()
        private const val PERMISSION_REQUEST_CODE = 200
        private const val VALUE_TIMER = 120000L
        private const val TICK_TIMER = 10L
        private const val PROGRESS_BAR_MAX_VALUE = 100
    }

    var listSong = mutableListOf<Song>()
    var progressCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleProgressBar()
    }

    private fun handleProgressBar() {
        object : CountDownTimer(VALUE_TIMER, TICK_TIMER) {
            override fun onFinish() {
                (activity as MusicActivity).finish()
            }

            override fun onTick(p0: Long) {
                progressCount += 1
                when (progressCount) {
                    5 -> {
                        if (!isCheckStorePermission()) {
                            requestStorePermission()
                            progressCount = 0
                        }
                    }
                    30 -> {
                        Thread(Runnable {
                            scanSongInStore().toCollection(listSong)
                            listSong.forEach {
                                d("XXXX",it.contentUri)
                            }
                        }).start()
                    }
                    PROGRESS_BAR_MAX_VALUE -> {
                        (activity as MusicActivity).initViewPager(listSong)
                        this.cancel()
                    }
                }
            }
        }.start()
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
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                )
                val name = cursor.getString(nameColumn)
                val song =
                    Song(id = id, nameSong = name, contentUri = contentUri.toString()).getData(
                        requireContext()
                    )
                songListsNew.add(song)
            }
        }
        return songListsNew
    }

    private fun isCheckStorePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStorePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }
}
