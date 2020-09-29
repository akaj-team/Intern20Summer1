package com.asiantech.intern20summer1.week12.data.source.remote

import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI
import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import com.asiantech.intern20summer1.week12.data.source.remote.network.ApiService
import io.reactivex.Single
import retrofit2.Response

class UserRemoteDataSource(val api: ApiService? = ClientAPI.createPost()) : UserDataSource {
    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        api?.login(username, password)

    override fun addUser(userRegister: UserRegister): Single<Response<UserAutoSignIn>>? =
        api?.addNewUserRegister(userRegister)
}
