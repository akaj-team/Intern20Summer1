package com.asiantech.intern20summer1.w11.data.source

import android.content.Context
import com.asiantech.intern20summer1.w11.data.source.datasource.SharedPreferencesDataSource
import com.asiantech.intern20summer1.w11.data.source.local.SharedPreferencesLocalDataSource

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is LocalRepository
 */
class LocalRepository(context: Context) : SharedPreferencesDataSource {
    private val sharedPreferences = SharedPreferencesLocalDataSource(context)

    override fun putToken(token: String) {
        sharedPreferences.putToken(token)
    }

    override fun getToken(): String =
        sharedPreferences.getToken()


    override fun putIdUser(idUser: Int) {
        sharedPreferences.putIdUser(idUser)
    }

    override fun getIdUser(): Int =
        sharedPreferences.getIdUser()

    override fun putIsLogin(isLogin: Boolean) {
        sharedPreferences.putIsLogin(isLogin)
    }

    override fun getIsLogin(): Boolean =
        sharedPreferences.getIsLogin()
}
