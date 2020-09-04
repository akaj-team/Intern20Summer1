package com.asiantech.intern20summer1.week10.model

class Post() {

    companion object {
        private const val RANDOM_LIKES_RANGE = 4
    }

    internal var userName: String? = null
    internal var imageUri: String? = null
    internal var caption: String? = null
    internal var isLiked: Boolean = false
    internal var likes: Int = 0
    internal var isPluralLike: Boolean = false

    constructor(
        name: String,
        imageUri: String,
        caption: String,
        isLiked: Boolean,
        likes: Int,
        isPluralLike: Boolean
    ) : this() {
        this.userName = name
        this.imageUri = imageUri
        this.caption = caption
        this.isLiked = isLiked
        this.likes = likes
        this.isPluralLike = isPluralLike
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
                    random,
                    random > 1
                )
            )
        }
        return posts
    }
}
