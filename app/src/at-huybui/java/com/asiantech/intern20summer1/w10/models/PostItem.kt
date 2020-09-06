package com.asiantech.intern20summer1.w10.models

import com.google.gson.annotations.SerializedName

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is PostItem class. It is model for the post item
 */
data class PostItem(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("user_id") var user_id: Int = 0,
    @SerializedName("content") var content: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("created_at") var created_at: String = "",
    @SerializedName("like_count") var like_count: Int = 0,
    @SerializedName("like_flag") var like_flag: Boolean = false
)

/**
 * Created by at-huybui on 01/09/2020.
 * This is ResponseLike class. It is model for response from api with like
 */
data class ResponseLike(
    @SerializedName("message") var message: String = "",
    @SerializedName("like_count") var like_count: Int = 0,
    @SerializedName("like_flag") var like_flag: Boolean = false
)

/**
 * Created by at-huybui on 01/09/2020.
 * This is ResponsePost class. It is model for response from api
 */
data class ResponsePost(
    @SerializedName("message") var message: String = ""
)

/**
 * Created by at-huybui on 01/09/2020.
 * This is PostContent class. It is model for content the new post
 */
data class PostContent(@SerializedName("content") var content: String)
