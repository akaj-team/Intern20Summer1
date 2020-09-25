package com.asiantech.intern20summer1.week12.data.source

import android.content.Context
import com.asiantech.intern20summer1.week12.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.week12.data.source.local.SharedPreferencesLocalDataSource

class LocalRepository(private val context: Context) : LocalDataSource {
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
