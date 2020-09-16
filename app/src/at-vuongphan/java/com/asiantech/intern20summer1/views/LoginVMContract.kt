package com.asiantech.intern20summer1.views

import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import io.reactivex.Single
import retrofit2.Response

interface LoginVMContract {
    fun login(username: String, password: String): Single<Response<UserAutoSignIn>>?
}
