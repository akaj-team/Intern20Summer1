package com.asiantech.intern20summer1.week10.model

import com.google.gson.annotations.SerializedName

class ToggleLikeResponse (
    @SerializedName("message") var message: String = "",
    @SerializedName("like_count") var likeCount: Int = 0,
    @SerializedName("like_flag") var likeFlag: Boolean = false
)
