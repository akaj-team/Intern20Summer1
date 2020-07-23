package com.asiantech.intern20summer1.week4.views

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.models.User
import kotlinx.android.synthetic.`at-linhle`.activity_home.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class HomeActivity : AppCompatActivity() {

    private var user: User? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        changeColorStatusBar()
        getUser()
        user?.avatar?.let { loadImageFromStorage(it) }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun changeColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        // Add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        // Change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.titleColor
        }
        // Clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    // Receive data
    private fun getUser() {
        val intent = intent
        user = intent.getParcelableExtra("user")
        tvUserEmail.text = user?.email
        tvUserFullName.text = user?.fullName
        tvUserPhone.text = user?.phone
    }

    // Load image
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
