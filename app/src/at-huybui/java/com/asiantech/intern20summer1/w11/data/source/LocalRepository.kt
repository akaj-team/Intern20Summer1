package com.asiantech.intern20summer1.w11.data.source

import android.content.Context
import android.net.Uri
import com.asiantech.intern20summer1.w11.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.w11.data.source.local.LocalLocalDataSource
import okhttp3.MultipartBody

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is LocalRepository
 */
class LocalRepository(context: Context) : LocalDataSource {
    private val sharedPreferences = LocalLocalDataSource(context)

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

    override fun createMultiPartBody(uri: Uri?): MultipartBody.Part? =
        sharedPreferences.createMultiPartBody(uri)
}
