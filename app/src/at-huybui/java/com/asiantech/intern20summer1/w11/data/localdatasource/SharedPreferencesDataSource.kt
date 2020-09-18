package com.asiantech.intern20summer1.w11.data.localdatasource

import android.content.Context

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is SharedPreferences TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
interface SharedPreferencesDataSource {
    fun putToken(context: Context, token: String)
    fun getToken(context: Context): String
    fun putIdUser(context: Context, idUser: Int)
    fun getIdUser(context: Context): Int
    fun putIsLogin(context: Context, isLogin: Boolean)
    fun getIsLogin(context: Context): Boolean
}
     