package com.asiantech.intern20summer1.w12.data.source.datasource

import com.asiantech.intern20summer1.w12.data.model.User
import io.reactivex.Single
import retrofit2.Response

interface LoginDataSource {
    fun login(email: String, password: String): Single<Response<User>>?
}
