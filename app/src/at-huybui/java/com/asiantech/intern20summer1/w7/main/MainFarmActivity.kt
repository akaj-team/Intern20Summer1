package com.asiantech.intern20summer1.w7.main

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.launcher.RegisterFarmFragment
import com.asiantech.intern20summer1.w7.model.Account
import kotlinx.android.synthetic.`at-huybui`.activity_main_farm.*

class MainFarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_farm)

    }


    private fun checkData() {
        val user = intent.getSerializableExtra(RegisterFarmFragment.KEY_PUT) as? Account
        user?.let {
            tvTest?.append("name: ${it.userName} \n")
            tvTest?.append("university: ${it.university} \n")
            tvTest?.append("home town: ${it.homeTown} \n")
            it.imgUri?.let { imgUri ->
                imgAvatarHome?.setImageURI(Uri.parse(imgUri))
            }
        }
    }
}
