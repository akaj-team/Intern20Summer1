package com.asiantech.intern20summer1.week10.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.week10.api.RetrofitClient
import com.asiantech.intern20summer1.week10.model.User
import com.asiantech.intern20summer1.week10.other.TOKEN_KEY
import com.asiantech.intern20summer1.week10.other.USER_DATA_PREFS_WEEK_10
import retrofit2.Call
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class SplashActivityWeekTen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configStatusBarColor()

        val sharePref = getSharedPreferences(USER_DATA_PREFS_WEEK_10, Context.MODE_PRIVATE)

        val autoSignIn =
            sharePref.getString(TOKEN_KEY, null)?.let { token ->
                RetrofitClient.createUserService()?.autoSignIn(
                    token
                )
            }

        autoSignIn?.enqueue(object : retrofit2.Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@SplashActivityWeekTen, t.message, Toast.LENGTH_LONG)
                    .show()
                openSignInActivity()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    loginApp()
                } else {
                    openSignInActivity()
                }
            }
        })
    }

    private fun loginApp() {
        startActivity(Intent(this, TimeLineActivity::class.java))
        finish()
    }

    private fun openSignInActivity() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    private fun configStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
