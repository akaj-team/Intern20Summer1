package com.asiantech.intern20summer1.w9.models

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is data class for song item in musics recycler view
 */

@Entity(tableName = Song.TABLE_NAME)
data class Song(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID) var id: Long = 0,
    @ColumnInfo(name = NAME_SONG) var nameSong: String = "null",
    @ColumnInfo(name = NAME_SINGER) var nameSinger: String = "null",
    @ColumnInfo(name = LENGTH_SONG) var lengthSong: String = "00:00",
    @ColumnInfo(name = CONTENT_URI) var contentUri: String = "null"
) {
    companion object {
        const val COLUMN_ID = BaseColumns._ID
        const val TABLE_NAME = "villains"
        const val NAME_SONG = "name_song"
        const val NAME_SINGER = "name_singer"
        const val LENGTH_SONG = "length_song"
        const val CONTENT_URI = "content_uri"
    }
}
