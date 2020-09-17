package com.asiantech.intern20summer1.week12

import com.google.gson.annotations.SerializedName

class UserRegister(
    @SerializedName("email") var email: String,
    @SerializedName("full_name") var name: String,
    @SerializedName("password") var password: String
)
