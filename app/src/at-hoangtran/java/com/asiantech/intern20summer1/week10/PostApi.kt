package com.asiantech.intern20summer1.week10

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostApi {

    @GET("/api/posts")
    fun getListPost(@Header("token") token: String): Call<MutableList<Post>>

    @POST("/api/post")
    @Multipart
    fun addNewPost(
        @Header("token") token: String,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: Body
    ): Call<PostResponse>

    @POST("/api/post/{id} ")
    @Multipart
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: Body
    ): Call<PostResponse>

    @POST("/api/post/{id}/like")
    fun updatePostLike(@Header("token") token: String, @Path("id") id: Int): Call<LikeResponse>
}
