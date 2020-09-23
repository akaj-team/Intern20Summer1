package com.asiantech.intern20summer1.week12.data.source.remote

import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import com.asiantech.intern20summer1.week12.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.network.ApiClient
import io.reactivex.Single
import retrofit2.Response

class LoginRemoteDataSource(val api: ApiUserService? = ApiClient.createUserService()) :
    LoginDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        api?.login(email, password)

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        api?.addNewUser(userRegister)
}
