package com.asiantech.intern20summer1.api.w10.remotedatassource

import com.asiantech.intern20summer1.api.w10.ClientAPI
import com.asiantech.intern20summer1.api.w10.datasource.UserDataSource
import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import com.asiantech.intern20summer1.model.w10.UserRegister
import io.reactivex.Single
import retrofit2.Response

class UserRemoteDataSource : UserDataSource {
    val callRx = ClientAPI.createUserService()
    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        callRx?.login(username, password)

    override fun addUser(userRegister: UserRegister): Single<Response<UserAutoSignIn>>? =
        callRx?.addNewUserRegister(userRegister)
}
