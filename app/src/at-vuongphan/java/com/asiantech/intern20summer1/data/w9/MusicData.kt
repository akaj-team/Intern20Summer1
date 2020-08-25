package com.asiantech.intern20summer1.data.w9

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.asiantech.intern20summer1.R
import java.util.concurrent.TimeUnit

object MusicData {
    private val musicData = mutableListOf<Music>()
    fun getMusic(context: Context): MutableList<Music> {
        musicData.clear()
        val songCursor = context.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val idColumn = songCursor?.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        while (songCursor != null && songCursor.moveToNext()) {
            val id = idColumn?.let { songCursor.getLong(it) }
            val songDuration =
                songCursor.getInt(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val songName =
                songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val songArtist =
                songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val uri: Uri? =
                id?.let {
                    ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        it
                    )
                }
            val albumId: Long? =
                songCursor.getLong(songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = albumId?.let { ContentUris.withAppendedId(sArtworkUri, it) }
            Log.d("aaa", "getMusic: ")
            musicData.apply {
                uri?.let {
                    albumArtUri?.let { it1 ->
                        Music(
                            songDuration, songName, it, songArtist,
                            it1
                        )
                    }
                }?.let { add(it) }
            }
        }
        return musicData
    }

    internal fun convertUriToBitmap(path: Uri, context: Context): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    internal
    fun toMin(millis: Long, context: Context): String {
        return context.getString(
            R.string.tv_duration, TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
    }
}
