package com.asiantech.intern20summer1.w12.data.network

import com.asiantech.intern20summer1.w12.data.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @POST("/api/user")
    fun addUser(@Body user: UserRegister): Single<Response<User>>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<Response<User>>

    @GET("/api/autosignin")
    fun autoSignIn(@Header("token") token: String?): Single<Response<User>>

    @GET("/api/posts")
    fun getAllPost(@Header("token") token: String): Single<Response<MutableList<Post>>>

    @POST("/api/post/{id}/like")
    fun likePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Single<Response<LikeResponse>>

    @Multipart
    @POST("api/post")
    fun createPost(
        @Header("token") token: String,
        @Part("body") body: PostContent,
        @Part image: MultipartBody.Part? = null
    ): Single<Response<StatusResponse>>

    @POST("/api/post/{id} ")
    @Multipart
    fun updatePost(
        @Header("token") token: String?,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: PostContent
    ): Single<Response<StatusResponse>>
}
