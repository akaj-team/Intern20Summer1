package com.asiantech.intern20summer1.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.SignInActivity
import com.asiantech.intern20summer1.model.User
import kotlinx.android.synthetic.`at-phuongle`.fragment_login.*


class LoginFragment : Fragment() {
    private var user: User? = null
    private var email: String? = null
    private var password: String? = null

    companion object {
        const val DATA_KEY = "register_data"

        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as SignInActivity).handleEmailEditText(edtEmail, btnLogin)
        (activity as SignInActivity).handlePasswordEditText(edtPass, btnLogin)
        handleLoginTextView()

        user = getDataFromRegisterFragment()
        bindDataToEditText(user)

        handleLoginButton()
    }

    private fun handleLoginButton() {
        btnLogin.setOnClickListener {
            if (isValidAccount()) {
                Toast.makeText(activity as SignInActivity, "Valid account!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val dialog = AlertDialog.Builder(context)
                dialog.setCancelable(false)
                dialog.setTitle("Warning")
                dialog.setMessage("Your email or password is wrong!")
                dialog.setPositiveButton("Cancel",
                    DialogInterface.OnClickListener { _, _ ->
                        //Action for "Cancel".
                    })

                val alert: AlertDialog = dialog.create()
                alert.show()
            }
        }
    }

    private fun handleLoginTextView() {
        tvLoginRegister.setOnClickListener {
            (activity as SignInActivity).replaceFragment(RegisterFragment.newInstance(), true)
        }
    }

    private fun getDataFromRegisterFragment(): User? {
        val bundle = this.arguments
        if (bundle != null) {
            return bundle.getParcelable(DATA_KEY)
        }
        return null
    }

    private fun bindDataToEditText(user: User?) {
        edtEmail.setText(user?.email)
        edtPass.setText(user?.password)
    }

    private fun isValidAccount(): Boolean {
        return edtEmail.text.toString() == user?.email && edtPass.text.toString() == user?.password
    }
}
