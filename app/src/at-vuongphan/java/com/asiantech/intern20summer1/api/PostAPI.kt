package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.model.NewPost
import retrofit2.Call
import retrofit2.http.*

interface PostAPI {
    @GET("api/posts")
    fun getAllPost(): Call<MutableList<NewPost>>

    @POST("api/posts")
    fun addNewPost(@Body newFeedModel: NewPost): Call<NewPost>

    @PUT("api/posts/{id}")
    fun updateNewPost(@Path("id") id: Int, @Body newFeed: NewPost): Call<NewPost>

    @DELETE("api/posts/{id}")
    fun deleteNewPost(@Path("id") id: Int): Call<NewPost>

}