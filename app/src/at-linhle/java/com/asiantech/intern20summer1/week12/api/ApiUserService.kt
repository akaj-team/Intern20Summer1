package com.asiantech.intern20summer1.week12.api

import com.asiantech.intern20summer1.week12.models.User
import com.asiantech.intern20summer1.week12.models.UserRegister
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiUserService {

    @POST("/api/user")
    fun addNewUser(@Body userRegister: UserRegister): Single<Response<User>>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<Response<User>>

    @GET("/api/autosignin")
    fun handleAutoSignIn(@Header("token") token: String): Single<User>
}
