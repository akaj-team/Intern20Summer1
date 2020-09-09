package com.asiantech.intern20summer1.week10.api

import com.asiantech.intern20summer1.week10.model.User
import com.asiantech.intern20summer1.week10.model.UserLogin
import com.asiantech.intern20summer1.week10.model.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("api/user")
    fun createUser(@Body userRegister: UserRegister): Call<User>

    @GET("api/autosignin")
    fun autoSignIn(@Header("token") token: String): Call<User>

    @POST("api/login")
    fun login(@Body userLogin: UserLogin): Call<User>
}
