package com.asiantech.intern20summer1.w12.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.model.User

class HomeActivity : AppCompatActivity() {

    private var user: User = User(0,"","","")

    companion object{
        internal const val ADD_TO_BACK_STACK_KEY= "BACK_STACK"
        internal const val IMAGE_FOLDER_URL = "https://at-a-trainning.000webhostapp.com/images/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w12_activity_home)
//        getDataFromLogin()
        replaceFragmentHome(HomeFragment.newInstance(user),false)
    }

    internal fun replaceFragmentHome(fragment: Fragment , backStack : Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutHomeActivity, fragment)
        if (backStack){
            fragmentTransaction.addToBackStack(ADD_TO_BACK_STACK_KEY)
        }
        fragmentTransaction.commit()
    }

//    private fun getDataFromLogin(){
//        (intent?.getSerializableExtra(USER_KEY_LOGIN) as? User)?.let {
//            user.id = it.id
//            user.email = it.email
//            user.full_name = it.full_name
//            user.token = it.token
//        }
//    }
}
