package com.asiantech.intern20summer1.week10.api

import com.asiantech.intern20summer1.week10.models.Body
import com.asiantech.intern20summer1.week10.models.LikeResponse
import com.asiantech.intern20summer1.week10.models.Post
import com.asiantech.intern20summer1.week10.models.PostResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiPostService {

    @GET("/api/posts")
    fun getListPost(@Header("token") token: String): Call<MutableList<Post>>

    @POST("/api/post")
    @Multipart
    fun addNewPost(
        @Header("token") token: String,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: Body
    ): Call<PostResponse>

    @POST("/api/post/{id}/like")
    fun updatePostLike(@Header("token") token: String, @Path("id") id: Int): Call<LikeResponse>
}
