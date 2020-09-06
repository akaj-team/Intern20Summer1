package com.asiantech.intern20summer1.w10.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.data.User
import com.asiantech.intern20summer1.w10.fragment.HomeFragment

class HomeActivity : AppCompatActivity() {

    private var user: User = User(0,"","","")

    companion object{
        internal const val IMAGE_FOLDER_URL = "https://at-a-trainning.000webhostapp.com/images/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w10_activity_home)
        getDataFromLogin()
        val toolbar = findViewById<Toolbar>(R.id.toolBarNewFeed)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = user.full_name
        Log.d("TAG000", "onCreateView: ")
        replaceFragmentHome(HomeFragment.newInstance(user))
    }

    internal fun replaceFragmentHome(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutHomeActivity, fragment, null)
            .commit()
    }

    private fun getDataFromLogin(){
        (intent?.getSerializableExtra("user") as? User)?.let {
            user.id = it.id
            user.email = it.email
            user.full_name = it.full_name
            user.token = it.token
        }
    }
}
