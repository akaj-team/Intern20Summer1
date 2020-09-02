package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w0.AccountTest
import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.models.PostItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiAccountService {

    companion object{
        private const val PART_CREATE_USER_POST = "api/user"
    }

    @GET("api/autosignin")
    fun autoSignIn(): Call<Account>

    @POST(PART_CREATE_USER_POST)
    fun createUser(@Query("model") accountTest: AccountTest) : Call<Account>
}


