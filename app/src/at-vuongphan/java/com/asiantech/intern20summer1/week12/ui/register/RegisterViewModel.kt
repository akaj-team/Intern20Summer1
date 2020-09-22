package com.asiantech.intern20summer1.week12.ui.register

import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import io.reactivex.Single
import retrofit2.Response

class RegisterViewModel(private val userRepository: UserDataSource) : RegisterVMContract {
    override fun addUserRegister(userRegister: UserRegister): Single<Response<UserAutoSignIn>>? =
        userRepository.addUser(userRegister)
}
