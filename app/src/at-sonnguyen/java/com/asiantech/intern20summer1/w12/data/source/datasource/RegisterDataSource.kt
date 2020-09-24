package com.asiantech.intern20summer1.w12.data.source.datasource

import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface RegisterDataSource {
    fun register(userRegister: UserRegister): Single<Response<User>>?
}
