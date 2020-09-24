package com.asiantech.intern20summer1.w12.data.source.remote.network

import com.asiantech.intern20summer1.w12.data.model.LikeResponse
import com.asiantech.intern20summer1.w12.data.model.Post
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import io.reactivex.Single
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

    @GET("/api/posts")
    fun getAllPost(@Header("token") token: String): Single<Response<MutableList<Post>>>

    @POST("/api/post/{id}/like")
    fun likePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Single<Response<LikeResponse>>
}
