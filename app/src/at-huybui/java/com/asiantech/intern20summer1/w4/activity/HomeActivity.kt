package com.asiantech.intern20summer1.w4.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.fragment.SignInFragment.Companion.KEY_USE
import com.asiantech.intern20summer1.w4.model.Account
import kotlinx.android.synthetic.`at-huybui`.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        receiveData()
    }

    /**
     * This function receive data from sign in fragment
     */
    private fun receiveData() {
        val use = intent.getSerializableExtra(KEY_USE) as? Account
        use?.let { it ->
            if (it.avatarUri.isNotEmpty()) {
                imgAvatarHome.setImageURI(Uri.parse(it.avatarUri))
            }
            tvEmailHome.text = it.email
            tvNameHome.text = it.name
            tvNumberPhoneHome.text = it.mobilePhone
        }
    }
}
