package com.asiantech.intern20summer1.w7.model

import java.io.Serializable

data class AccountClass(
    val userName: String = "",
    val university: String = "",
    val homeTown: String = "",
    val imgUri: String? = null
) : Serializable
