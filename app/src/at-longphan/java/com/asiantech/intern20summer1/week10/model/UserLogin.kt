package com.asiantech.intern20summer1.week10.model

import com.google.gson.annotations.SerializedName

class UserLogin (
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = ""
)