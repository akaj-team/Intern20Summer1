package com.asiantech.intern20summer1.w4.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.fragment.SignInFragment

class SignInActivity : AppCompatActivity() {
    companion object {
        private const val ADD_TO_BACK_STACK_KEY_NAME = "add to back Stack"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        addSignUpFragment()
//        handleEnableButton()
        //replaceSignInFragment()

    }

    private fun addSignUpFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = SignInFragment()
        fragmentTransaction.replace(R.id.llFragment, fragment, ADD_TO_BACK_STACK_KEY_NAME)
//        fragmentTransaction.addToBackStack(ADD_TO_BACK_STACK_KEY_NAME)
        fragmentTransaction.commit()
    }


//    private fun handleEnableButton() {
//        btnSignIn.isEnabled = isValidEmail(edtSignInEmail.toString())&&isValidPassword(edtSignInPassword.toString())
//    }

//    fun isValidEmail(string: String) =
//        android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()

//    private fun isValidPassword(string: String): Boolean {
//        if (string.length !in 8..16) {
//            return false
//        } else {
//            for (i in string.indices) {
//                if (string[i].isDigit()) return true
//            }
//        }
//        return false
//    }
}
