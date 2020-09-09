package com.asiantech.intern20summer1.week10.api

import com.asiantech.intern20summer1.week10.model.CreatePostBody
import com.asiantech.intern20summer1.week10.model.Post
import com.asiantech.intern20summer1.week10.model.StatusResponse
import com.asiantech.intern20summer1.week10.model.ToggleLikeResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostService {

    @GET("api/posts")
    fun getPosts(@Header("token") token: String?): Call<List<Post>>

    @POST("api/post/{id}/like")
    fun toggleLikeFlag(@Header("token") token: String?, @Path("id") postId: Int?): Call<ToggleLikeResponse>

    @POST("/api/post")
    @Multipart
    fun createPost(
        @Header("token") token: String,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: CreatePostBody
    ): Call<StatusResponse>

    @POST("/api/post/{id}")
    @Multipart
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: CreatePostBody
    ): Call<StatusResponse>

    /*@POST("api/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): Call<User>*/

    /*@POST("api/login")
    fun login(@Body userLogin: UserLogin): Call<User>*/
}
