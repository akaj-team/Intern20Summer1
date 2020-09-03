package com.asiantech.intern20summer1.model

import com.google.gson.annotations.SerializedName

data class NewPost(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("content") var content: String,
    @SerializedName("image") var image: String,
    @SerializedName("created_at") var created_at: String,
    @SerializedName("like_count") var like_count: Int,
    @SerializedName("like_flag") var like_flag: Boolean
)

data class ItemLike(
    @SerializedName("message") var message: String,
    @SerializedName("like_count") var like_count: Int,
    @SerializedName("like_flag") var like_flag: Boolean
)
