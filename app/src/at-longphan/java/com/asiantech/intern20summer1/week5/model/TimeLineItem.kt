package com.asiantech.intern20summer1.week5.model

class TimeLineItem() {

    internal var name: String? = null
    internal var imageUri: String? = null
    internal var content: String? = null
    internal var isLiked: Boolean = false
    internal var likes: Int = 0
    internal var isPluralLike: Boolean = false

    constructor(
        name: String,
        imageUri: String,
        content: String,
        isLiked: Boolean,
        likes: Int,
        isPluralLike: Boolean
    ) : this() {
        this.name = name
        this.imageUri = imageUri
        this.content = content
        this.isLiked = isLiked
        this.likes = likes
        this.isPluralLike = isPluralLike
    }

    constructor(timeLineItem : TimeLineItem) : this() {
        this.name = timeLineItem.name
        this.imageUri = timeLineItem.imageUri
        this.content = timeLineItem.content
        this.isLiked = timeLineItem.isLiked
        this.likes = timeLineItem.likes
        this.isPluralLike = timeLineItem.isPluralLike
    }

    fun createTimeLineItemsList(numItems: Int): MutableList<TimeLineItem> {
        var timeLineItems = mutableListOf<TimeLineItem>()
        for (i in 1..numItems) {
            var random = (0..4).random()
            timeLineItems.add(
                TimeLineItem(
                    "Name $i",
                    "",
                    "This is content, this is content this is content this is content",
                    random != 0 && i % 2 == 0,
                    random,
                    random > 1
                )
            )
        }
        return timeLineItems
    }
}
