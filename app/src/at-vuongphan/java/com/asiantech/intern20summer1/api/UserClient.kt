package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.model.UserLogin
import com.asiantech.intern20summer1.model.UserAutoSignIn
import com.asiantech.intern20summer1.model.UserRegister
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserClient {
    @GET("api/autosignin")
    fun getAllUserAutoSignIn(): Call<MutableList<UserAutoSignIn>>

    @GET("api/autosignin")
    fun getSecret(@Header("Authorization") authToken: String): Call<ResponseBody>

    @POST("api/user")
    fun addNewUserRegister(@Body newUserRegister: UserRegister): Call<UserAutoSignIn>

    @POST("api/login")
    fun addNewUserLogin(@Body newUserLogin: UserLogin): Call<UserLogin>
}
