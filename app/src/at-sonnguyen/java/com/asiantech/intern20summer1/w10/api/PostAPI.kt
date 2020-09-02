package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.data.Post
import retrofit2.Call
import retrofit2.http.*

interface PostAPI {

    @GET("api/posts")
    fun getAllPost(): Call<MutableList<Post>>

    @POST("api/posts")
    fun addNewPost(@Body newPost: Post): Call<Post>

    @PUT("api/posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: Post): Call<Post>

    @DELETE("api/posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Post>
}
