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
 * This is LoginVM
 */
class LoginVM(
    private val loginRepository: LoginRepository,
    private val localRepository: LocalRepository
) : LoginVMContract {

    override fun autoSignIn(token: String): Single<Response<Account>>? =
        loginRepository.autoSignIn(token)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doAfterSuccess { response ->
                if (response.isSuccessful) {
                    response.body()?.let { account ->
                        localRepository.putIsLogin(true)
                        localRepository.putToken(account.token)
                        localRepository.putIdUser(account.id)
                    }
                }
            }

    override fun login(email: String, password: String): Single<Response<Account>>? {
        return loginRepository
            .login(email, password)
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
    }

    override fun createUser(request: RequestAccount): Single<Response<Account>>? =
        loginRepository.createUser(request)

    override fun getToken(): String? = localRepository.getToken()

    override fun putToken(token: String) {
        localRepository.putToken(token)
    }

    override fun putIdUser(idUser: Int) {
        localRepository.putIdUser(idUser)
    }

    override fun getIdUser(): Int? = localRepository.getIdUser()

    override fun putIsLogin(isLogin: Boolean) {
        localRepository.putIsLogin(isLogin)
    }

    override fun getIsLogin(): Boolean? = localRepository.getIsLogin()
}
