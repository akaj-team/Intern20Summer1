package com.asiantech.intern20summer1.week5.models

data class TimeLineItem(
    val userName: String,
    val avatar: Int,
    val picture: Int,
    var countLike: Int,
    var isLiked: Boolean
)