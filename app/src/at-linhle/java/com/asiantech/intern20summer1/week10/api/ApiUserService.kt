package com.asiantech.intern20summer1.week10.api

import com.asiantech.intern20summer1.week10.models.User
import com.asiantech.intern20summer1.week10.models.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiUserService {

    @POST("/api/user")
    fun addNewUser(@Body userRegister: UserRegister): Call<User>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): Call<User>
}
