package com.asiantech.intern20summer1.w11.ui.fragment.login

import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import io.reactivex.Single
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is LoginMVCotract
 */
interface LoginVMContract {

    fun autoSignIn(token: String): Single<Response<Account>>?

    fun login(
        email: String = "",
        password: String = ""
    ): Single<Response<Account>>?

    fun createUser(request: RequestAccount): Single<Response<Account>>?

    fun getToken(): String?

    fun putToken(token: String)

    fun putIdUser(idUser: Int)

    fun getIdUser(): Int?

    fun putIsLogin(isLogin: Boolean)

    fun getIsLogin(): Boolean?
}
