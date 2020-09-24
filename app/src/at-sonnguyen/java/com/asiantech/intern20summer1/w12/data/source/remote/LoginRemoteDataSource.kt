package com.asiantech.intern20summer1.w12.data.source.remote

import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.network.APIClient
import io.reactivex.Single
import retrofit2.Response

class LoginRemoteDataSource(private val api: APIClient) : LoginDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        api.createService()?.login(email, password)
}
