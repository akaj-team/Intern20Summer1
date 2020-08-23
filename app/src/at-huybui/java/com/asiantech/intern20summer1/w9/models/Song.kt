package com.asiantech.intern20summer1.w9.models

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is data class for song item in musics recycler view
 */

@Entity(tableName = Song.TABLE_NAME)
data class Song(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID) var id: Long = 0,
    @ColumnInfo(name = NAME_SONG) var nameSong: String? = "null",
    @ColumnInfo(name = NAME_SINGER) var singer: String? = "null",
    @ColumnInfo(name = DURATION_SONG) var duration: String? = "00:00",
    @ColumnInfo(name = CONTENT_URI) var contentUri: String = "null"
):Serializable {
    companion object {
        const val COLUMN_ID = BaseColumns._ID
        const val TABLE_NAME = "villains"
        const val NAME_SONG = "name_song"
        const val NAME_SINGER = "name_singer"
        const val DURATION_SONG = "length_song"
        const val CONTENT_URI = "content_uri"
    }
}
