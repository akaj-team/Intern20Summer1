package com.asiantech.intern20summer1.week10.model

import com.google.gson.annotations.SerializedName

class Post() {

    companion object {
        private const val RANDOM_LIKES_RANGE = 4
    }

    @SerializedName("id") internal var id: Int? = null
    @SerializedName("user_id") internal var userId: String? = null
    @SerializedName("image") internal var image: String? = null
    @SerializedName("content") internal var content: String? = null
    @SerializedName("created_at") internal var createAt: String? = null
    @SerializedName("like_flag") internal var likeFlag: Boolean = false
    @SerializedName("like_count") internal var likeCount: Int = 0
    internal var isPluralLike: Boolean = false

    constructor(
        userId: String,
        image: String,
        content: String,
        likeFlag: Boolean,
        likeCount: Int
    ) : this() {
        this.userId = userId
        this.image = image
        this.content = content
        this.likeFlag = likeFlag
        this.likeCount = likeCount
        this.isPluralLike = likeCount > 1
    }

    fun createTimeLineItemsList(numItems: Int): MutableList<Post> {
        val posts = mutableListOf<Post>()
        for (i in 1..numItems) {
            val random = (0..RANDOM_LIKES_RANGE).random()
            posts.add(
                Post(
                    "Name $i",
                    "",
                    "This is content, this is content this is content this is content",
                    random != 0 && i % 2 == 0,
                    random
                )
            )
        }
        return posts
    }
}