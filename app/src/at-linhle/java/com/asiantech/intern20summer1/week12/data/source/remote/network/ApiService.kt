package com.asiantech.intern20summer1.week12.data.source.remote.network

import com.asiantech.intern20summer1.week12.data.model.LikeResponse
import com.asiantech.intern20summer1.week12.data.model.Post
import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/api/user")
    fun addNewUser(@Body userRegister: UserRegister): Single<Response<User>>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<Response<User>>

    @GET("/api/posts")
    fun getListPost(@Header("token") token: String): Single<Response<MutableList<Post>>>

    @POST("/api/post/{id}/like")
    fun updatePostLike(
        @Header("token") token: String,
        @Path("id") id: Int
    ): Single<Response<LikeResponse>>
}
