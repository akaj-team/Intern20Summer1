package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {
    @GET("api/user")
    fun getAllUser(): Call<MutableList<User>>

    @POST("api/user")
    fun addNewUser(@Body newFeedModel: User): Call<User>

    @PUT("api/user/{id}")
    fun updateUser(@Path("id") id: Int, @Body newFeed: User): Call<User>

    @DELETE("api/user/{id}")
    fun deleteUser(@Path("id") id: Int): Call<User>
}
