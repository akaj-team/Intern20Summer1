package com.asiantech.intern20summer1.w11.ui.fragment.login

import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import com.asiantech.intern20summer1.w11.data.source.LocalRepository
import com.asiantech.intern20summer1.w11.data.source.LoginRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is LoginVM TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class LoginVM(
    private val loginRepository: LoginRepository,
    private val localRepository: LocalRepository
) : LoginVMContract {

    override fun autoSignIn(token: String): Single<Response<Account>>? =
        loginRepository.autoSignIn(localRepository.getToken())
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSuccess { response ->
                if (response.isSuccessful) {
                    response.body()?.let { account ->
                        localRepository.putIsLogin(true)
                        localRepository.putToken(account.token)
                        localRepository.putIdUser(account.id)
                    }
                }
            }

    override fun login(email: String, password: String): Single<Response<Account>>? {
        TODO("Not yet implemented")
    }

    override fun createUser(request: RequestAccount): Single<Response<Account>>? {
        TODO("Not yet implemented")
    }

    override fun getToken(): String? {
        TODO("Not yet implemented")
    }

    override fun putToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun putIdUser(idUser: Int) {
        TODO("Not yet implemented")
    }

    override fun getIdUser(): Int? {
        TODO("Not yet implemented")
    }

    override fun putIsLogin(isLogin: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getIsLogin(): Boolean? {
        TODO("Not yet implemented")
    }
}
