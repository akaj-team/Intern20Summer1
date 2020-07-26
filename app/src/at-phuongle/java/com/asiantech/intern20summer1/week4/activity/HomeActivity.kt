package com.asiantech.intern20summer1.week4.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.model.User
import kotlinx.android.synthetic.`at-phuongle`.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var user: User? = null

    companion object {
        const val LOGIN_DATA_KEY = "login_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user = getDataFromRegisterFragment()
        bindData()
    }

    private fun getDataFromRegisterFragment(): User? {
        val bundle: Bundle? = intent.extras

        bundle?.let {
            bundle.apply {
                val data: User? = getParcelable(LOGIN_DATA_KEY)
                if (data != null) {
                    return data
                }
            }
        }
        return null
    }

    private fun bindData() {
        if (user != null) {
            imgHomeAvatar.setImageURI(Uri.parse(user?.avatarUri))
            tvHomeFullName.text = user?.fullName
            tvHomeEmail.text = user?.email
            tvHomeMobile.text = user?.mobileNumber
        }
    }
}
