package com.asiantech.intern20summer1

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_w4_home.*

@Suppress("CAST_NEVER_SUCCEEDS")
class W4HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_w4_home)

        val user = User("", "", "", "", "")

        val intent = intent
        ((intent.getParcelableExtra("user")) as? User)?.let {
            tv_name.text = it.name
            tv_mobile.text = it.mobile
            tv_email.text = it.email
            img_home_avatar.setImageURI(Uri.parse(it.avatar))
        }
    }
}
