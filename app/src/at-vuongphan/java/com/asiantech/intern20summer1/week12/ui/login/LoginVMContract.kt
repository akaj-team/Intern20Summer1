package com.asiantech.intern20summer1.week12.ui.login

import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import io.reactivex.Single
import retrofit2.Response

interface LoginVMContract {
    fun login(username: String, password: String): Single<Response<UserAutoSignIn>>?
    fun putToken(token: String)
    fun putIdUser(idUser: Int)
    fun putIsLogin(isLogin: Boolean)
}
