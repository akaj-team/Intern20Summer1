package com.asiantech.intern20summer1.w11.data.source

import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import com.asiantech.intern20summer1.w11.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.w11.data.source.remote.LoginRemoteDataSource
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is LoginRepository TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class LoginRepository : LoginDataSource {
    private val loginRemote = LoginRemoteDataSource()

    override fun autoSignIn(token: String): Single<Response<Account>>? =
        loginRemote.autoSignIn(token)

    override fun login(email: String, password: String): Single<Response<Account>>? =
        loginRemote.login(email, password)

    override fun createUser(request: RequestAccount): Single<Response<Account>>? =
        loginRemote.createUser(request)
}
