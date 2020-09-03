package com.asiantech.intern20summer1.w10.models

import com.google.gson.annotations.SerializedName

data class PostItem(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("content") var content: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("created_at") var created_at: String = "",
    @SerializedName("like_count") var like_count: Int = 0,
    @SerializedName("like_flag") var like_flag: Boolean = false
)

data class ResponseLike(
    @SerializedName("message") var message: String = "",
    @SerializedName("likeCount") var likeCount: Int = 0
)

/**
{
"id": 1,
"content": "This is post content",
"image": "filename.png",
"created_at": "2020-08-31T17:07:29.000000Z",
"like_count": 1,
"like_flag": true
}
 */
