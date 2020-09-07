package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.data.LikeResponse
import com.asiantech.intern20summer1.w10.data.Post
import com.asiantech.intern20summer1.w10.data.PostContent
import com.asiantech.intern20summer1.w10.data.StatusResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostAPI {

    @GET("/api/posts")
    fun getAllPost(@Header("token") token: String): Call<MutableList<Post>>

    @POST("/api/post/{id}/like")
    fun likePost(@Header("token") token: String, @Path("id") id: Int = 0): Call<LikeResponse>

    @Multipart
    @POST("api/post")
    fun createPost(
        @Header("token") token: String,
        @Part("body") body: PostContent,
        @Part image: MultipartBody.Part? = null
    ): Call<StatusResponse>

    @POST("/api/post/{id} ")
    @Multipart
    fun updatePost(
        @Header("token") token: String?,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: PostContent
    ): Call<StatusResponse>
}
