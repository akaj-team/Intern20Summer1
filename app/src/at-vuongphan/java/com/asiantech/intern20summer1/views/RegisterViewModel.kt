package com.asiantech.intern20summer1.views

import com.asiantech.intern20summer1.api.w10.datasource.UserDataSource
import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import com.asiantech.intern20summer1.model.w10.UserRegister
import io.reactivex.Single
import retrofit2.Response

class RegisterViewModel(private val userRepository: UserDataSource) : RegisterVMContract {
    override fun addUserRegister(userRegister: UserRegister): Single<Response<UserAutoSignIn>>? =
        userRepository.addUser(userRegister)
}
