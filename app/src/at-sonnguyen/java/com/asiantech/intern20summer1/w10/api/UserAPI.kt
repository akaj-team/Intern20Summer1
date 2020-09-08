package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.data.User
import com.asiantech.intern20summer1.w10.data.UserRegister
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @POST("/api/user")
    fun addUser(@Body user: UserRegister): Call<User>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): Call<User>

    @GET("/api/autosignin")
    fun autoSignIn(@Header("token") token : String) : Call<User>
}
