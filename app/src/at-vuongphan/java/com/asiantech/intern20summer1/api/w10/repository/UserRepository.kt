package com.asiantech.intern20summer1.api.w10.repository

import com.asiantech.intern20summer1.api.w10.datasource.UserDataSource
import com.asiantech.intern20summer1.api.w10.remotedatassource.UserRemoteDataSource
import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import com.asiantech.intern20summer1.model.w10.UserRegister
import io.reactivex.Single
import retrofit2.Response

class UserRepository : UserDataSource {
    private val userRemoteDataSource = UserRemoteDataSource()
    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        userRemoteDataSource.login(username, password)

    override fun addUser(userRegister: UserRegister): Single<Response<UserAutoSignIn>>? =
        userRemoteDataSource.addUser(userRegister)
}
