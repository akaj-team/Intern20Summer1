package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.model.ApiResponse
import com.asiantech.intern20summer1.model.NewPost
import com.asiantech.intern20summer1.model.Post
import com.asiantech.intern20summer1.model.ResponseLike
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostsAPI {
    @GET("api/posts")
    fun getPost(@Header("token") token: String): Call<MutableList<NewPost>>

    @DELETE("api/post/{id}")
    fun deletePosts(@Header("token") token: String, @Path("id") id: Int): Call<ApiResponse>

    @Multipart
    @POST("api/post")
    fun createPost(
        @Header("token") token: String,
        @Part("body") body: Post,
        @Part image: MultipartBody.Part? = null
    ): Call<ApiResponse>

    @Multipart
    @POST("api/post/{id}")
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int,
        @Part("body") body: Post,
        @Part image: MultipartBody.Part? = null
    ): Call<ApiResponse>

    @POST("api/post/{id}/like")
    fun likePost(@Header("token") token: String, @Path("id") id: Int = 0): Call<ResponseLike>
}
