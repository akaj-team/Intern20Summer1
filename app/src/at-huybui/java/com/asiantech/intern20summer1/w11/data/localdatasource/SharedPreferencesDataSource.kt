package com.asiantech.intern20summer1.w11.data.localdatasource

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is SharedPreferences
 */
interface SharedPreferencesDataSource {
    fun putToken(token: String)
    fun getToken(): String
    fun putIdUser(idUser: Int)
    fun getIdUser(): Int
    fun putIsLogin(isLogin: Boolean)
    fun getIsLogin(): Boolean
}
     