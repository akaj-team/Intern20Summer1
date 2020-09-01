package com.asiantech.intern20summer1.model

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("email") var email: String,
    @SerializedName("full_name") var full_name: String,
    @SerializedName("token") var token: String
)
