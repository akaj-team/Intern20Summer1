package com.asiantech.intern20summer1.model

data class TimelineItem(
    var avatar: String,
    var name: String,
    var image: String,
    var like: Int,
    var comment: String,
    var isLiked: Boolean = false
) {
}
