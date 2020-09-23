package com.asiantech.intern20summer1.w11.data.source.remote

import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import com.asiantech.intern20summer1.w11.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.w11.data.source.remote.network.ApiClient
import io.reactivex.Single
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is AccountRemoteDataSource
 */
class LoginRemoteDataSource : LoginDataSource {
    private val apiService = ApiClient.getApiService()
    override fun autoSignIn(token: String): Single<Response<Account>>? =
        apiService?.autoSignIn(token)

    override fun login(email: String, password: String): Single<Response<Account>>? =
        apiService?.login(email, password)


    override fun createUser(request: RequestAccount): Single<Response<Account>>? =
        apiService?.createUser(request)
}
