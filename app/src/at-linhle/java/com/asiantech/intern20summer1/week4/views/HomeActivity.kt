package com.asiantech.intern20summer1.week4.views

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.extensions.changeColorStatusBar
import com.asiantech.intern20summer1.week4.fragments.SignInFragment.Companion.KEY_STRING_USER
import com.asiantech.intern20summer1.week4.models.User
import kotlinx.android.synthetic.`at-linhle`.activity_home.*

class HomeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        changeColorStatusBar(window, titleColor)
        getUser()
    }

    // Receive data
    private fun getUser() {
        val user: User? = intent.getParcelableExtra(KEY_STRING_USER)
        tvUserEmail.text = user?.email
        tvUserFullName.text = user?.fullName
        tvUserPhone.text = user?.phone
        imgUserAvatar.setImageURI(Uri.parse(user?.avatar))
    }
}
