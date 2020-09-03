package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.data.User
import com.asiantech.intern20summer1.w10.data.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    @POST("/api/user")
    fun addUser(@Body user: UserRegister): Call<User>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): Call<User>
}