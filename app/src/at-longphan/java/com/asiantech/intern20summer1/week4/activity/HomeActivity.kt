package com.asiantech.intern20summer1.week4.activity

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.model.User
import com.asiantech.intern20summer1.week4.other.SignInActivityData
import kotlinx.android.synthetic.`at-longphan`.activity_home.*

class HomeActivity : AppCompatActivity() {
    companion object {
        var userLogin = User()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        initData()
        loadDataToView()
    }

    private fun initData() {
        userLogin = intent.getParcelableExtra(
            SignInActivityData.SIGN_IN_USER.data) as User
    }

    private fun loadDataToView() {
        tvFullName.text = userLogin.fullName
        tvEmail.text = userLogin.email
        tvMobileNumber.text = userLogin.mobileNumber
        if (userLogin.avatarUri != null) {
            imgAvatar.setImageURI(Uri.parse(userLogin.avatarUri))
        }
    }
}
