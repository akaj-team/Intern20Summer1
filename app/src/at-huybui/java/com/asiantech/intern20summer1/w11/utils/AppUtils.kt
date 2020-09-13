package com.asiantech.intern20summer1.w11.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is AppUtils class. this contain method util for project
 */
class AppUtils {
    companion object {
        internal const val NAME_PREFERENCE = "preference"
        internal const val KEY_IS_LOGIN = "key_is_login"
        internal const val KEY_TOKEN = "key_token_login"
        private const val KEY_ID_USER = "key_id_user"
        private const val FORMAT_CODE_BEFORE = "yyyy-MM-dd'T'HH:mm:ss"
        private const val FORMAT_CODE_AFTER = "HH:mm dd/MM/yyyy"
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

    internal fun convertDate(date: String): String {
        var dateReturn = ""
        SimpleDateFormat(FORMAT_CODE_BEFORE, Locale.US).parse(date)?.let {
            dateReturn = SimpleDateFormat(FORMAT_CODE_AFTER, Locale.US).format(it)
        }
        return dateReturn
    }
}
