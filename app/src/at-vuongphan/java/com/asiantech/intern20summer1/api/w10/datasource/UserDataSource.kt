package com.asiantech.intern20summer1.api.w10.datasource

import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import com.asiantech.intern20summer1.model.w10.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface UserDataSource {
    fun login(username: String, password: String): Single<Response<UserAutoSignIn>>?

    fun addUser(userRegister: UserRegister): Single<Response<UserAutoSignIn>>?
}
