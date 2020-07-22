package com.asiantech.intern20summer1.w4.activity


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
        val intent1 = intent
        (intent1.getSerializableExtra("data") as? User)?.let {
            userHome.email = it.email
            userHome.fullName = it.fullName
            userHome.phoneNumber = it.phoneNumber
            userHome.password = it.password
            userHome.avatarUri = it.avatarUri
            imgAvatarHome.setImageURI(Uri.parse(userHome.avatarUri))
            edtHomePhoneNumber.setText("" + it.phoneNumber)
            edtHomeEmail.setText("" + it.email)
            edtHomeFullName.setText("" + it.fullName)

        }
        btnConfirm.setOnClickListener {
            Toast.makeText(this, "" + userHome.password, Toast.LENGTH_SHORT).show()
        }
    }
}
