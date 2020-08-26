package com.asiantech.intern20summer1.week9ver1.model

class Song {

    internal var id: Long?
    internal var title: String
    internal var artist: String

    constructor(
        id: Long,
        title: String,
        artist: String
    ) {
        this.id = id
        this.title = title
        this.artist = artist
    }
}
