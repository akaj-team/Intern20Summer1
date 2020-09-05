package com.asiantech.intern20summer1.model.w10

import com.google.gson.annotations.SerializedName

class Post(
    @SerializedName("content") var content: String
)

data class ApiResponse(
    @SerializedName("message") val message: String
)

data class ResponseLike(
    @SerializedName("message") var message: String = "",
    @SerializedName("likeCount") var likeCount: Int = 0,
    @SerializedName("like_flag") var like_flag: Boolean
)
