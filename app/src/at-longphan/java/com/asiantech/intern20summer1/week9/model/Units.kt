package com.asiantech.intern20summer1.week9.model

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import java.util.concurrent.TimeUnit

object Units {

    internal const val ACTION_PLAY_PAUSE = "playpause"
    internal const val ACTION_SKIP_NEXT = "skipnext"
    internal const val ACTION_PREVIOUS = "previous"
    internal const val ACTION_KILL_MEDIA = "killmedia"
    private const val SONG_TIME_FORMAT = "%02d:%02d"
    private var songs: ArrayList<Song> = arrayListOf()

    fun convertTimeMusic(millis: Int): String {
        return String.format(
            SONG_TIME_FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(millis.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    millis.toLong()
                )
            )
        )
    }

    fun songArt(path: Uri, context: Context): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    @SuppressLint("Recycle", "InlinedApi")
    fun insertData(context: Context): ArrayList<Song> {
        songs.clear()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val selection =
            MediaStore.Audio.Media.IS_MUSIC + " != ? "

        val selectionArgs = arrayOf<String>(
            "0"
        )

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )

        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

        val musicCursor: Cursor? = context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        while (musicCursor != null && musicCursor.moveToNext()) {
            val songID =
                musicCursor.getInt(musicCursor.getColumnIndex(MediaStore.Audio.Media._ID))

            val songName =
                musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))

            val songArtist =
                musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))

            val songDuration =
                musicCursor.getInt(musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))

            val albumId: Long =
                musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val sArtWorkUri: Uri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri: String = ContentUris.withAppendedId(sArtWorkUri, albumId).toString()

            val songPath = Uri.withAppendedPath(uri, songID.toString()).toString()

            songs.add(
                Song(
                    songPath,
                    songName,
                    songArtist,
                    albumArtUri,
                    //ContentUris.withAppendedId(uri, songIDLong).toString(),
                    songDuration
                )
            )
        }
        musicCursor?.close()

        return songs
    }
}
