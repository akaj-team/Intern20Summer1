package com.asiantech.intern20summer1.w0

import com.google.gson.annotations.SerializedName

data class AccountTest(
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("full_name") var full_name: String = ""
)

/**
{
"id": 1,
"email": "email@gmail.com",
"full_name": "Cuong Cao",
"token": "e8903400da23d9f7b5bea4ad8353196c"
}
 */
