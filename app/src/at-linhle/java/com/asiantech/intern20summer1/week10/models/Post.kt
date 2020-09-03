package com.asiantech.intern20summer1.week10.models

import com.google.gson.annotations.SerializedName

class Post(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("image") val image: String,
    @SerializedName("create_at") val createAt: String,
    @SerializedName("like_count") var likeCount: Int,
    @SerializedName("like_flag") var likeFlag: Boolean
)
