package com.asiantech.intern20summer1.w11.data.localdatasource

import android.content.Context

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 18/09/2020.
 * This is SharedPreferencesLocalDataSource TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class SharedPreferencesLocalDataSource(private val context: Context) : SharedPreferencesDataSource {
    companion object {
        internal const val NAME_PREFERENCE = "preference"
        internal const val KEY_IS_LOGIN = "key_is_login"
        internal const val KEY_TOKEN = "key_token_login"
        private const val KEY_ID_USER = "key_id_user"
    }

    override fun putToken(token: String) {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        preference.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getToken(): String {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return preference.getString(KEY_TOKEN, "").toString()
    }

    override fun putIdUser(idUser: Int) {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        preference.edit().putInt(KEY_ID_USER, idUser).apply()
    }

    override fun getIdUser(): Int {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return preference.getInt(KEY_ID_USER, 0)
    }

    override fun putIsLogin(isLogin: Boolean) {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        preference.edit().putBoolean(KEY_IS_LOGIN, isLogin).apply()
    }

    override fun getIsLogin(): Boolean {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return preference.getBoolean(KEY_IS_LOGIN, false)
    }
}
     