package com.asiantech.intern20summer1.week12.data.source.datasource

import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface UserDataSource {
    fun login(username: String, password: String): Single<Response<UserAutoSignIn>>?

    fun addUser(userRegister: UserRegister): Single<Response<UserAutoSignIn>>?
}
