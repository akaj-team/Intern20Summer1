package com.asiantech.intern20summer1.week12.ui.login

import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.source.LocalRepository
import com.asiantech.intern20summer1.week12.data.source.UserRepository
import com.asiantech.intern20summer1.week12.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoginViewModel(
    private val userRepository: UserDataSource,
    private val localRepository: LocalDataSource
) : LoginVMContract {

    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        userRepository.login(username, password)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSuccess {
                it.body().let {
                    putIsLogin(true)
                    it?.token?.let { it1 -> putToken(it1) }
                    it?.id?.let { it1 -> putIdUser(it1) }
                }
            }

    override fun getToken(): String? {
        TODO("Not yet implemented")
    }

    override fun putToken(token: String) {
        localRepository.putToken(token)
    }

    override fun putIdUser(idUser: Int) {
        localRepository.putIdUser(idUser)
    }

    override fun getIdUser(): Int? {
        TODO("Not yet implemented")
    }

    override fun putIsLogin(isLogin: Boolean) {
        localRepository.putIsLogin(isLogin)
    }

    override fun getIsLogin(): Boolean? {
        TODO("Not yet implemented")
    }
}
