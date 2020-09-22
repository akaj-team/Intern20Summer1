package com.asiantech.intern20summer1.week12

import io.reactivex.Single
import retrofit2.Response

interface LoginDataSource{
    fun login(email: String, password: String): Single<Response<User>>?
}
