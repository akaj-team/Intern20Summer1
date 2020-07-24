package com.asiantech.intern20summer1.w4.account

import java.io.Serializable

class User(
    var fullName: String,
    var email: String,
    var phoneNumber: String,
    var password: String,
    var avatarUri: String
) : Serializable
