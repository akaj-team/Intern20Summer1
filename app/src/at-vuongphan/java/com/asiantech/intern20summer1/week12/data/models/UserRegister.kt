package com.asiantech.intern20summer1.week12.data.models

import com.google.gson.annotations.SerializedName

class UserRegister(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("full_name") var full_name: String
)
