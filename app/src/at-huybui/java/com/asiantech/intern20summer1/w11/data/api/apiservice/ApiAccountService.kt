package com.asiantech.intern20summer1.w11.data.api.apiservice

import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 02/09/2020.
 * This is ApiAccountService class. It will progress Api for Account model
 */

interface ApiAccountService {

    companion object {
        private const val PART_CREATE_POST = "/api/user"
        private const val PART_AUTO_SIGN_IN = "/api/autosignin"
        private const val PART_LOGIN = "/api/login"
    }

    @GET(PART_AUTO_SIGN_IN)
    fun autoSignIn(@Header("token") token: String): Observable<Response<Account>>?

    @POST(PART_LOGIN)
    @FormUrlEncoded
    fun login(
        @Field("email") email: String = "",
        @Field("password") password: String = ""
    ): Observable<Response<Account>>?

    @POST(PART_CREATE_POST)
    fun createUser(@Body request: RequestAccount): Observable<Response<Account>>?
}
