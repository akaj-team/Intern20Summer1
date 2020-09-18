package com.asiantech.intern20summer1.w12.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {

    private var token : String? = null
    private var id : Int? = null
    private var email : String? = null
    private var fullName : String? = null

    companion object{
        internal const val SHARED_PREFERENCE_FILE = "share-preference-file"
        internal const val SHARED_PREFERENCE_TOKEN_KEY = "shared-preference-token-key"
        internal const val SHARED_PREFERENCE_ID_KEY = "shared-preference-id-key"
        internal const val SHARED_PREFERENCE_EMAIL_KEY = "shared-preference-email-key"
        internal const val SHARED_PREFERENCE_FULL_NAME_KEY = "shared-preference-full-name-key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w12_activity_login)
//        handleAutoLogin()
        replaceFragment(LoginFragment.newInstance())
    }
    internal fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutMain,fragment,null)
            .addToBackStack(null)
            .commit()
    }

    private fun checkSharedPreference() : Boolean{
        val sharedPreference = getSharedPreferences(SHARED_PREFERENCE_FILE, MODE_PRIVATE)
        if (sharedPreference?.getString(SHARED_PREFERENCE_TOKEN_KEY,null) == null){
            return false
        }
        token = sharedPreference.getString(SHARED_PREFERENCE_TOKEN_KEY, token)
        id = sharedPreference.getInt(SHARED_PREFERENCE_ID_KEY, 0)
        email = sharedPreference.getString(SHARED_PREFERENCE_EMAIL_KEY, email)
        fullName = sharedPreference.getString(SHARED_PREFERENCE_FULL_NAME_KEY, fullName)
        return true
    }
//    private fun handleAutoLogin(){
//        if (checkSharedPreference()){
//            val service = APIClient.createServiceClient()?.create(UserAPI::class.java)
//            val call =
//                token?.let { service?.autoSignIn(it) }
//            call?.enqueue(object : Callback<User> {
//                override fun onResponse(call: Call<User>, response: Response<User>) {
//                    if (response.isSuccessful) {
//                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
//                        intent.putExtra(LoginFragment.FULL_NAME_KEY, fullName)
//                        intent.putExtra(LoginFragment.TOKEN_KEY, token)
//                        response.body().apply {
//                            intent.putExtra(USER_KEY_LOGIN, this)
//                        }
//                        startActivity(intent)
//                        finish()
//                    }
//                }
//
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    replaceFragment(LoginFragment.newInstance())
//                }
//          })
//        }else{
//            replaceFragment(LoginFragment.newInstance())
//        }
//    }
}
