package com.asiantech.intern20summer1.w10.models

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("email") var email: String = "",
    @SerializedName("full_name") var full_name: String = "",
    @SerializedName("token") var token: String = ""
)

/**
{
"id": 1,
"email": "email@gmail.com",
"full_name": "Cuong Cao",
"token": "e8903400da23d9f7b5bea4ad8353196c"
}
 */
