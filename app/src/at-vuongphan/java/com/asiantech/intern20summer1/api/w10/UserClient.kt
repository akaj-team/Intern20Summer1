package com.asiantech.intern20summer1.api.w10

import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import com.asiantech.intern20summer1.model.w10.UserRegister
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserClient {
    @POST("api/user")
    fun addNewUserRegister(@Body newUserRegister: UserRegister): Single<Response<UserAutoSignIn>>

    @POST("/api/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<Response<UserAutoSignIn>>
}
