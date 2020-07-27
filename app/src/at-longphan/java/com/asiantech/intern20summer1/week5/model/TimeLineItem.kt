package com.asiantech.intern20summer1.week5.model

class TimeLineItem() {
    var name: String? = null
    var imageUri: String? = null
    var isLiked: Boolean = false
    var likes: Int = 0
    var isPluralLike: Boolean = false

    constructor(timeLineItem: TimeLineItem) : this() {
        this.name = timeLineItem.name
        this.imageUri = timeLineItem.imageUri
        this.isLiked = timeLineItem.isLiked
        this.likes = timeLineItem.likes
        this.isPluralLike = timeLineItem.isPluralLike
    }

    constructor(
        name: String,
        imageUri: String,
        isLiked: Boolean,
        likes: Int,
        isPluralLike: Boolean
    ) : this() {
        this.name = name
        this.imageUri = imageUri
        this.isLiked = isLiked
        this.likes = likes
        this.isPluralLike = isPluralLike
    }

    private  var lastTimeLineItemId = 0

    fun createTimeLineItemsList(numItems: Int): MutableList<TimeLineItem> {
        var timeLineItems = mutableListOf<TimeLineItem>()
        for (i in 1..numItems) {
            var random = (1..10).random()
            timeLineItems.add(
                TimeLineItem(
                    "Name ${++lastTimeLineItemId}",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ7Nd6wrunZbX3dGyoBjDWWAwro6Qj3T3IYrA&usqp=CAU",
                    i % 2 == 0,
                    random,
                    random > 1
                )
            )
        }
        return timeLineItems
    }
}
