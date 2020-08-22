package com.asiantech.intern20summer1.w9.data

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.asiantech.intern20summer1.R
import java.util.concurrent.TimeUnit

object SongData {
    private var songList = mutableListOf<Song>()
    fun getSong(context: Context): MutableList<Song> {
        val cursor = context.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val columnId = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        while (cursor != null && cursor.moveToNext()) {
            val id = columnId?.let { cursor.getLong(it) }
            val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val singerName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val uri: Uri? =
                id?.let {
                    ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        it
                    )
                }
            val albumId: Long? =
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = albumId?.let { ContentUris.withAppendedId(sArtworkUri, it) }
            songList.apply {
                add(Song(duration, songName, singerName, albumArtUri))
            }
        }
        return songList
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

    internal fun toMin(millis: Long, context: Context): String {
        return context.getString(
            R.string.duration_text_view, TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)
                    )
        )
    }
}