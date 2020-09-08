package com.asiantech.intern20summer1.week10

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body

interface UserApi {

    @POST("/api/user")
    fun addNewUser(@Body userRegister: UserRegister): Call<User>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): Call<User>

    @GET("/api/autoSignIn")
    fun handleAutoSignIn(@Header("token") token: String): Call<User>
}
