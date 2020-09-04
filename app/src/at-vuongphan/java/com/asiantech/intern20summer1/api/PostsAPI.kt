package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.model.NewPost
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostsAPI {
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

    @Multipart
    @POST("api/post")
    fun createPost(
        @Header("token") token: String,
        @Part image: MultipartBody.Part,
        @Part("body") body: RequestBody
    ): Call<RequestBody>
}
