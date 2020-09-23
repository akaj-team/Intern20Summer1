package com.asiantech.intern20summer1.week12.data.source.datasource

import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface LoginDataSource {
    fun login(email: String, password: String): Single<Response<User>>?

    fun register(userRegister: UserRegister): Single<Response<User>>?
}
