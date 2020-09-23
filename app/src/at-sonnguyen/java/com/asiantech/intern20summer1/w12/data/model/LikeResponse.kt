package com.asiantech.intern20summer1.w12.data.model

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("message") var message : String,
    @SerializedName("like_count") var like_count : Int,
    @SerializedName("like_flag") var like_flag : Boolean
)
