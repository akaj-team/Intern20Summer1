package com.asiantech.intern20summer1.week9

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi

object Utils {
    private var musicList: ArrayList<Song> = arrayListOf()
    fun convertToBitmap(context: Context, path: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Recycle")
    fun getSongFromDevice(context: Context): ArrayList<Song> {
        musicList.clear()
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
                val currentTitle = cursor.getString(title)
                val currentArtist = cursor.getString(artist)
                val currentDuration = cursor.getInt(duration)
                val imgUri = ContentUris.withAppendedId(uri, currentId)
                musicList.add(
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
        return musicList
    }
}
