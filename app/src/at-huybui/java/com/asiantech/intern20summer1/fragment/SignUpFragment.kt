package com.asiantech.intern20summer1.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.SignInFragment.Companion.REGEX_PASSWORD
import kotlinx.android.synthetic.`at-huybui`.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private var emailBuffer = ""
    private var nameBuffer = ""
    private var numberPhoneBuffer = ""
    private var passwordBuffer = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleForBackToSignInButton()
        handleForEmailEditText()
        handleForPasswordEditText()

    }

    private fun handleForBackToSignInButton() {
        btnBackToSignIn.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun handleForFullNameEditText() {

    }

    private fun handleForEmailEditText() {
        edtEmailSignUp.addTextChangedListener { text ->
            val pattern = PatternsCompat.EMAIL_ADDRESS.matcher(text.toString()).matches()
            setIconForEditText(edtEmailSignUp, pattern)
            emailBuffer = if (pattern) text.toString() else ""
        }
    }

    private fun handleForNumberPhoneEditText() {

    }

    private fun handleForPasswordEditText() {
        edtPasswordSignUp.addTextChangedListener { text ->
            val pattern = text.toString().matches(REGEX_PASSWORD)
            setIconForEditText(edtPasswordSignUp, pattern)
            passwordBuffer = if (pattern) text.toString() else ""
        }


    }

    private fun handleForRewritePasswordEditText() {

    }

    private fun handleForRegisterButton() {

    }

    @SuppressLint("NewApi")
    private fun setIconForEditText(editText: EditText, boolean: Boolean) {
        if (boolean) {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.icon_tick, 0)
        } else {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.icon_error, 0)
        }

    }
}
