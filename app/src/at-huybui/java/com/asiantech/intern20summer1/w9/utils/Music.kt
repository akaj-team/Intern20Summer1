package com.asiantech.intern20summer1.w9.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.asiantech.intern20summer1.w9.models.Song

class Music {
     fun getData(context: Context, song: Song): Song {
        var songNew = song
        MediaMetadataRetriever().apply {
            var name = "no name"
            var duration = "00:00"
            var author = "no name"
            setDataSource(context, Uri.parse(song.contentUri))
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.let {
                duration = convertDuration(it)
            }
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)?.let {
                author = it
            }
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)?.let {
                name = it
            }
            songNew = Song(song.id, name, author, duration, song.contentUri)
        }
        return songNew
    }

     fun getPicture(context: Context, song: Song): Bitmap? {
        var picture: Bitmap? = null
        MediaMetadataRetriever().apply {
            setDataSource(context, Uri.parse(song.contentUri))
            embeddedPicture?.let {
                picture = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        }
        return picture
    }

     fun convertDuration(duration: String?): String {
        var result = "00:00"
        duration?.let {
            val durationInt = it.toInt()
            val second = ((durationInt / 1000) % 60).toString().padStart(2, '0')
            val minute = ((durationInt / 1000) / 60).toString().padStart(2, '0')
            result = "$minute:$second"
        }
        return result
    }
}