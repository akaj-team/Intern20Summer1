package com.asiantech.intern20summer1.w12.api

import com.asiantech.intern20summer1.w12.model.User
import com.asiantech.intern20summer1.w12.model.UserRegister
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("/api/user")
    fun addUser(@Body user: UserRegister): Single<Response<User>>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<Response<User>>

    @GET("/api/autosignin")
    fun autoSignIn(@Header("token") token: String): Single<User>
}
