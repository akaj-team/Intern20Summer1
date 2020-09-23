package com.asiantech.intern20summer1.week12.data.model

import com.google.gson.annotations.SerializedName

class Post(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("content") var content: String,
    @SerializedName("image") val image: String,
    @SerializedName("created_at") val createAt: String,
    @SerializedName("like_count") var likeCount: Int,
    @SerializedName("like_flag") var likeFlag: Boolean
)

class PostResponse(@SerializedName("message") val message: String)

class Body(@SerializedName("content") var content: String)

class LikeResponse(
    @SerializedName("message") var message: String,
    @SerializedName("like_count") var likeCount: Int,
    @SerializedName("like_flag") var likeFlag: Boolean
)
