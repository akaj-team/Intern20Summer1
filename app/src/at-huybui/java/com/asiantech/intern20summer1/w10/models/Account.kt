package com.asiantech.intern20summer1.w10.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is Account class. It is model for account
 */
data class Account(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("email") var email: String = "",
    @SerializedName("full_name") var full_name: String = "",
    @SerializedName("token") var token: String = ""
):Serializable

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is RequestAccount class. It is model for request api with account to sign up
 */
data class RequestAccount(
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("full_name") var full_name: String = ""
)
