package com.asiantech.intern20summer1.model

import com.google.gson.annotations.SerializedName

class UserLogin(
    @SerializedName("email")
    var email: String,
    @SerializedName("full_name")
    var password: String
)
