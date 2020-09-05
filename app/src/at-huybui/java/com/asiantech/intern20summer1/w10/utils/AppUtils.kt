package com.asiantech.intern20summer1.w10.utils

import android.content.Context
import android.widget.Toast

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 05/09/2020.
 * This is AppUtils (TODO)
 */
class AppUtils {
    companion object {
        internal const val NAME_PREFERENCE = "preference"
        internal const val KEY_IS_LOGIN = "key_is_login"
        internal const val KEY_TOKEN = "key_token_login"
        private const val KEY_ID_USER = "key_id_user"
    }

    internal fun showToast(context: Context, any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, any.toString(), duration).show()
    }

    internal fun putToken(context: Context, token: String) {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        preference.edit().putString(KEY_TOKEN, token).apply()
    }


    internal fun getToken(context: Context): String {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return preference.getString(KEY_TOKEN, "").toString()
    }

    internal fun putIdUser(context: Context, id: Int) {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        preference.edit().putInt(KEY_ID_USER, id).apply()
    }

    internal fun getIdUser(context: Context): Int {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return preference.getInt(KEY_ID_USER, 0)
    }

    internal fun putIsLogin(context: Context, isLogin: Boolean) {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        preference.edit().putBoolean(KEY_IS_LOGIN, isLogin).apply()
    }

    internal fun getIsLogin(context: Context): Boolean {
        val preference = context.getSharedPreferences(
            NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return preference.getBoolean(KEY_IS_LOGIN, false)
    }
}
