package com.asiantech.intern20summer1.week12.data.source.remote

import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI
import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import com.asiantech.intern20summer1.week12.data.source.remote.network.UserClient
import io.reactivex.Single
import retrofit2.Response

class UserRemoteDataSource() : UserDataSource {
    val callRx = ClientAPI.createUserService()
    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        callRx?.login(username, password)

    override fun addUser(userRegister: UserRegister): Single<Response<UserAutoSignIn>>? =
        callRx?.addNewUserRegister(userRegister)
}
