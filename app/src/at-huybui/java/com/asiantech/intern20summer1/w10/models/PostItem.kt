package com.asiantech.intern20summer1.w10.models

data class PostItem(
    val content: String,
    val created_at: String,
    val id: Int,
    val image: String,
    val like_count: Int,
    val like_flag: Boolean
)