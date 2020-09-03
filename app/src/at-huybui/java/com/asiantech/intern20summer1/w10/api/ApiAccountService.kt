package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.models.RequestAccount
import retrofit2.Call
import retrofit2.http.*

interface ApiAccountService {

    companion object {
        private const val PART_CREATE_POST = "api/user"
        private const val PART_AUTO_SIGNIN = "api/autosignin"
        private const val PART_LOGIN = "/api/login"
    }

    @GET(PART_AUTO_SIGNIN)
    fun autoSignIn(): Call<Account>

    @POST(PART_LOGIN)
    @FormUrlEncoded
    fun login(
        @Field("email") email: String = "",
        @Field("password") password: String = ""
    ): Call<Account>

    @POST(PART_CREATE_POST)
    fun createUser(@Body request: RequestAccount): Call<Account>
}


