package com.asiantech.intern20summer1.week10.model

import com.google.gson.annotations.SerializedName

class Error (
    @SerializedName("message") var message: String
)