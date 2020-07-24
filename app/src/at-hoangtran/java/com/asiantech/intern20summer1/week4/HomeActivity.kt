package com.asiantech.intern20summer1.week4

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_w4_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_w4_home)

        val intent = intent
        ((intent.getParcelableExtra("user")) as? User)?.let {
            tv_name.text = it.name
            tv_mobile.text = it.mobile
            tv_email.text = it.email
            img_home_avatar.setImageURI(Uri.parse(it.avatar))
        }
    }
}
