package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.model.UserAutoSignIn
import com.asiantech.intern20summer1.model.UserRegister
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserClient {
    @GET("api/autosignin")
    fun getAllUserAutoSignIn(): Call<MutableList<UserAutoSignIn>>

    @GET("api/autosignin")
    fun getSecret(@Header("Authorization") authToken: String): Call<ResponseBody>

    @POST("api/user")
    fun addNewUserRegister(@Body newUserRegister: UserRegister): Call<UserAutoSignIn>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserAutoSignIn>
}
