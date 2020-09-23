package com.asiantech.intern20summer1.week12.data.source

import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import com.asiantech.intern20summer1.week12.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.LoginRemoteDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.network.ApiClient
import io.reactivex.Single
import retrofit2.Response

class LoginRepository : LoginDataSource {
    private val loginRemoteDataSource = LoginRemoteDataSource(ApiClient)

    override fun login(email: String, password: String): Single<Response<User>>? = loginRemoteDataSource.login(email, password)

    override fun register(userRegister: UserRegister): Single<Response<User>>? = loginRemoteDataSource.register(userRegister)
}
