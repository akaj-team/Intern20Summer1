package com.asiantech.intern20summer1.w11.data.repository

import android.content.Context
import com.asiantech.intern20summer1.w11.data.localdatasource.SharedPreferencesDataSource
import com.asiantech.intern20summer1.w11.data.localdatasource.SharedPreferencesLocalDataSource

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is LocalRepository TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class LocalRepository(private val context: Context) : SharedPreferencesDataSource {
    val sharedPreferences = SharedPreferencesLocalDataSource()

    override fun putToken(context: Context, token: String) {
        sharedPreferences.putToken(context, token)
    }

    override fun getToken(context: Context): String =
        sharedPreferences.getToken(context)


    override fun putIdUser(context: Context, idUser: Int) {
        sharedPreferences.putIdUser(context, idUser)
    }

    override fun getIdUser(context: Context): Int =
        sharedPreferences.getIdUser(context)

    override fun putIsLogin(context: Context, isLogin: Boolean) {
        sharedPreferences.putIsLogin(context, isLogin)
    }

    override fun getIsLogin(context: Context): Boolean =
        sharedPreferences.getIsLogin(context)
}
     