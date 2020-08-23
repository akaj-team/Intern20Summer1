package com.asiantech.intern20summer1.week9.model

class Song {

    internal var id: Int? = null
    internal var name: String? = null
    internal var singer: String? = null
    internal var length: String? = null
    internal var imageUrl: String? = null
    internal var status: Boolean = false

    constructor(
        id: Int,
        name: String,
        singer: String,
        length: String,
        imageUrl: String,
        status: Boolean
    ) {
        this.id = id
        this.name = name
        this.singer = singer
        this.length = length
        this.imageUrl = imageUrl
        this.status = status
    }
}