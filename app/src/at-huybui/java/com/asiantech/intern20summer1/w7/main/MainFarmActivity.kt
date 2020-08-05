package com.asiantech.intern20summer1.w7.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

class MainFarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_farm)
        handleReplaceFragment(VegetableFragment.newInstance())
    }

    private fun handleReplaceFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        nameBackStack: String = "null"
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerMain, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }


//    private fun checkData() {
//        val user = intent.getSerializableExtra(RegisterFarmFragment.KEY_PUT) as? Account
//        user?.let {
//            tvTest?.append("name: ${it.userName} \n")
//            tvTest?.append("university: ${it.university} \n")
//            tvTest?.append("home town: ${it.homeTown} \n")
//            it.imgUri?.let { imgUri ->
//                imgAvatarHome?.setImageURI(Uri.parse(imgUri))
//            }
//        }
//    }
}
