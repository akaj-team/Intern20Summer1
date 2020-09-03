package com.asiantech.intern20summer1.w10.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Account(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("email") var email: String = "",
    @SerializedName("full_name") var full_name: String = "",
    @SerializedName("token") var token: String = ""
):Serializable

data class RequestAccount(
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("full_name") var full_name: String = ""
)

/** request
        {
        "email": required | email format | max-length: 264
        "password": required | min-length: 8 | max-length: 16
        "full_name": required | max-length: 64
        }
 */

/** response
        {
        "id": 1,
        "email": "email@gmail.com",
        "full_name": "Cuong Cao",
        "token": "e8903400da23d9f7b5bea4ad8353196c"
        }
 */
