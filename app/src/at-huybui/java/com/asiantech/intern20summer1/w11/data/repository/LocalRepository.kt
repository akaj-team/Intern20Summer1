package com.asiantech.intern20summer1.w11.data.repository

import android.content.Context
import com.asiantech.intern20summer1.w11.data.localdatasource.SharedPreferencesDataSource
import com.asiantech.intern20summer1.w11.data.localdatasource.SharedPreferencesLocalDataSource

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is LocalRepository
 */
class LocalRepository(private val context: Context) : SharedPreferencesDataSource {
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
     