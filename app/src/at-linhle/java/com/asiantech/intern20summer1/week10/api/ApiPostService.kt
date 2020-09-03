package com.asiantech.intern20summer1.week10.api

import com.asiantech.intern20summer1.week10.models.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiPostService {

    @GET("/api/posts")
    fun getListPost(@Header("token") token : String): Call<MutableList<Post>>
}
