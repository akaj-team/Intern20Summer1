package com.asiantech.intern20summer1.week12.ui.login

import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import io.reactivex.Single
import retrofit2.Response

class LoginViewModel(private val userRepository: UserDataSource) : LoginVMContract {

    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        userRepository.login(username, password)
}
