package com.asiantech.intern20summer1.w10.data

import com.google.gson.annotations.SerializedName

class UserRegister(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("full_name") var fullName: String
)
