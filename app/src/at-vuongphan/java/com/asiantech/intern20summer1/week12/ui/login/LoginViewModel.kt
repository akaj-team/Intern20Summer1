package com.asiantech.intern20summer1.week12.ui.login

import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import io.reactivex.Single
import retrofit2.Response

class LoginViewModel(
    private val userRepository: UserDataSource,
    private val localRepository: LocalDataSource
) : LoginVMContract {

    override fun login(username: String, password: String): Single<Response<UserAutoSignIn>>? =
        userRepository.login(username, password)
            ?.doOnSuccess {
                it.body().let {
                    putIsLogin(true)
                    it?.token?.let { it1 -> putToken(it1) }
                    it?.id?.let { it1 -> putIdUser(it1) }
                }
            }
    
    override fun putToken(token: String) {
        localRepository.putToken(token)
    }

    override fun putIdUser(idUser: Int) {
        localRepository.putIdUser(idUser)
    }

    override fun putIsLogin(isLogin: Boolean) {
        localRepository.putIsLogin(isLogin)
    }
}
