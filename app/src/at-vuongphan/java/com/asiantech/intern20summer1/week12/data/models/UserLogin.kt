package com.asiantech.intern20summer1.week12.data.models

import com.google.gson.annotations.SerializedName

class UserLogin(
    @SerializedName("email")
    var email: String,
    @SerializedName("full_name")
    var password: String
)
