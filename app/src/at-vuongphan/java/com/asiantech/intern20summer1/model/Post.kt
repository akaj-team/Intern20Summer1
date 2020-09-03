package com.asiantech.intern20summer1.model

import com.google.gson.annotations.SerializedName

class Post(
    @SerializedName("body") var body: String,
    @SerializedName("image") var image: String
)