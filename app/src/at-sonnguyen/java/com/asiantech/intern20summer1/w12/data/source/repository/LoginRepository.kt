package com.asiantech.intern20summer1.w12.data.source.repository

import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.LoginRemoteDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.network.APIClient
import io.reactivex.Single
import retrofit2.Response

class LoginRepository : LoginDataSource {
    private val loginRemoteRepository = LoginRemoteDataSource(APIClient)

    override fun login(email: String, password: String): Single<Response<User>>? =
        loginRemoteRepository.login(email, password)
}
