package com.asiantech.intern20summer1.week12

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Body

interface UserApi {

    @POST("/api/user")
    fun addNewUser(@Body userRegister: UserRegister): Single<Response<User>>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<Response<User>>

    @GET("/api/autoSignIn")
    fun handleAutoSignIn(@Header("token") token: String): Single<Response<User>>
}
