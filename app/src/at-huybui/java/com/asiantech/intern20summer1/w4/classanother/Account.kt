package com.asiantech.intern20summer1.w4.classanother

import java.io.Serializable

data class Account(
    val name: String,
    val email: String,
    val mobilePhone: String,
    val passWord: String,
    val avatarUri: String
) : Serializable
