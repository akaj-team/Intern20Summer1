package com.asiantech.intern20summer1.w11.data.source.datasource

import android.net.Uri
import okhttp3.MultipartBody

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is SharedPreferences
 */
interface LocalDataSource {
    fun putToken(token: String)
    fun getToken(): String
    fun putIdUser(idUser: Int)
    fun getIdUser(): Int
    fun putIsLogin(isLogin: Boolean)
    fun getIsLogin(): Boolean
    fun createMultiPartBody(uri: Uri?): MultipartBody.Part?
}
