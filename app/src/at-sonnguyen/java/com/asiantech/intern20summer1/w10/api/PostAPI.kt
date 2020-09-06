package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.data.LikeResponse
import com.asiantech.intern20summer1.w10.data.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostAPI {

    @GET("/api/posts")
    fun getAllPost(@Header("token") token: String): Call<MutableList<Post>>

    @POST("/api/post/{id}/like")
    fun likePost(@Header("token") token: String, @Path("id") id: Int = 0): Call<LikeResponse>

}
