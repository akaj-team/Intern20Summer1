package com.asiantech.intern20summer1.week12.data.source

import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.UserRemoteDataSource
import io.reactivex.Single
import retrofit2.Response

class UserRepository : UserDataSource {
    private val userRemoteDataSource = UserRemoteDataSource()
    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        userRemoteDataSource.login(username, password)

    override fun addUser(userRegister: UserRegister): Single<Response<UserAutoSignIn>>? =
        userRemoteDataSource.addUser(userRegister)
}
