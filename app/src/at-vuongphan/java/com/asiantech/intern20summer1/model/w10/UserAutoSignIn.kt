package com.asiantech.intern20summer1.model.w10

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserAutoSignIn(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("email")
    var email: String,
    @SerializedName("full_name")
    var full_name: String,
    @SerializedName("token")
    var token: String
) : Serializable
