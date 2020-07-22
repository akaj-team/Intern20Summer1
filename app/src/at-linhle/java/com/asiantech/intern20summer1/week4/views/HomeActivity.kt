package com.asiantech.intern20summer1.week4.views

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.models.User
import kotlinx.android.synthetic.`at-linhle`.activity_home.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class HomeActivity : AppCompatActivity() {

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getUser()
        user?.avatar?.let { loadImageFromStorage(it) }
    }

    private fun getUser() {
        val intent = intent
        user = intent.getParcelableExtra("user")
        tvUserEmail.text = user?.email
        tvUserFullName.text = user?.fullName
        tvUserPhone.text = user?.phone
    }

    private fun loadImageFromStorage(path: String) {
        try {
            val f = File(path, "profile.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            val img: ImageView = findViewById<View>(R.id.imgUserAvatar) as ImageView
            img.setImageBitmap(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}
