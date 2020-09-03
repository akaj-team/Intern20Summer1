package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.model.NewPost
import retrofit2.Call
import retrofit2.http.*

interface PostAPI {
    @GET("api/posts")
    fun getPost(@Header("token") token: String): Call<MutableList<NewPost>>

    @DELETE("api/post/{id}")
    fun deletePosts(@Header("token") token: String, @Path("id") id: Int): Call<NewPost>

    @POST("api/post")
    fun addNewPost(@Body newFeedModel: NewPost): Call<NewPost>

    @PUT("api/post/{id}")
    fun updateNewPost(
        @Header("token") token: String,
        @Path("id") id: Int,
        @Body newFeed: NewPost
    ): Call<NewPost>
}
