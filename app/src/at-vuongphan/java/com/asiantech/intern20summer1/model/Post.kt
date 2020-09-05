package com.asiantech.intern20summer1.model

import com.google.gson.annotations.SerializedName

class Post(
    @SerializedName("content") var content: String
)

data class ApiResponse(
    @SerializedName("message") val message: String
)
