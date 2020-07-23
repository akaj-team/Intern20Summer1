package com.asiantech.intern20summer1.w4.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.account.User
import kotlinx.android.synthetic.`at-sonnguyen`.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var userHome = User("", "", "", "", "")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.decorView.setBackgroundColor(Color.WHITE)
        val intent1 = intent
        (intent1.getSerializableExtra("data") as? User)?.let {
            userHome.email = it.email
            userHome.fullName = it.fullName
            userHome.phoneNumber = it.phoneNumber
            userHome.password = it.password
            userHome.avatarUri = it.avatarUri
            edtHomePhoneNumber.setText("" + it.phoneNumber)
            edtHomeEmail.setText("" + it.email)
            edtHomeFullName.setText("" + it.fullName)
        }
        setImageViewResource(userHome.avatarUri)
    }

    private fun setImageViewResource(string: String) {
        if (string == "") {
            imgAvatarHome.setImageResource(R.mipmap.ic_launcher)
        } else {
            imgAvatarHome.setImageURI(Uri.parse(string))
        }
    }
}
