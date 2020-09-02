package com.asiantech.intern20summer1.w10.data

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("content") var content: String,
    @SerializedName("image") var image: String,
    @SerializedName("create_at") var create_at: String,
    @SerializedName("like_count") var like_count: Int,
    @SerializedName("like_flag") var like_flag: Boolean
)
