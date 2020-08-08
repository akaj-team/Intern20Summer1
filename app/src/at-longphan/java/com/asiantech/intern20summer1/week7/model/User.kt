package com.asiantech.intern20summer1.week7.model

class User {

    internal var userId: Int? = null
    internal var userName: String? = null
    internal var university: String? = null
    internal var homeTown: String? = null
    internal var avatar: String? = null

    constructor(
        userId: Int,
        userName: String,
        university: String,
        homeTown: String,
        avatar: String
    ) {
        this.userId = userId
        this.userName = userName
        this.university = university
        this.homeTown = homeTown
        this.avatar = avatar
    }
}
