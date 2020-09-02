package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {

    @POST("api/user")
    fun addUser(@Body user: User) : Call<User>

    @PUT("api/user/{id}")
    fun updateUser(@Path("id") id : Int , @Body user: User) : Call<User>
}