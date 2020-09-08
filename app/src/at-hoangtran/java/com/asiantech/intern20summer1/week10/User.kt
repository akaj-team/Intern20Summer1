package com.asiantech.intern20summer1.week10

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(
    @SerializedName("id") var id: Int,
    @SerializedName("email") var email: String,
    @SerializedName("full_name") var fullName: String,
    @SerializedName("token") var token: String
) : Serializable
