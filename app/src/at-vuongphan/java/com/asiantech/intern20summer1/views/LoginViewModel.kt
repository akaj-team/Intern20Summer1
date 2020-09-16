package com.asiantech.intern20summer1.views

import com.asiantech.intern20summer1.api.w10.datasource.UserDataSource
import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import io.reactivex.Single
import retrofit2.Response

class LoginViewModel(private val userRepository: UserDataSource) : LoginVMContract {

    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        userRepository.login(username, password)
}
