package com.asiantech.intern20summer1.week10.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.activity.TimeLineActivity
import com.asiantech.intern20summer1.week10.api.RetrofitClient
import com.asiantech.intern20summer1.week10.model.User
import com.asiantech.intern20summer1.week10.model.UserLogin
import com.asiantech.intern20summer1.week10.other.*
import com.asiantech.intern20summer1.week4.other.isValidEmail
import com.asiantech.intern20summer1.week4.other.isValidPassword
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_in_w10.*
import retrofit2.Call
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class SignInFragment : Fragment() {

    private lateinit var loginUser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in_w10, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleListeners() {
        handleEditTextEmailIdListener()
        handleEditTextPasswordListener()
        handleButtonSignInListener()
        handleTextViewRegisterNowListener()
    }

    private fun handleEditTextEmailIdListener() {
        edtEmailIdFragmentSignInW10?.addTextChangedListener {
            btnSignInW10?.isEnabled =
                isValidEmail(it.toString()) && isValidPassword(edtPasswordFragmentSignInW10.text.toString())
            changeStatusForSignInButton()
        }
    }

    private fun handleEditTextPasswordListener() {
        edtPasswordFragmentSignInW10?.addTextChangedListener {
            btnSignInW10?.isEnabled =
                isValidPassword(it.toString()) && isValidEmail(edtEmailIdFragmentSignInW10.text.toString())
            changeStatusForSignInButton()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleButtonSignInListener() {
        btnSignInW10?.setOnClickListener {
            val email = edtEmailIdFragmentSignInW10?.text.toString()
            val password = edtPasswordFragmentSignInW10?.text.toString()

            val callApi =
                RetrofitClient.createUserService()
                    ?.login(UserLogin(email, password))

            val builder = AlertDialog.Builder(context)
            builder.setView(R.layout.progress_dialog_loading)
            val progressDialogLoading = builder.create()
            progressDialogLoading?.show()

            callApi?.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    progressDialogLoading?.dismiss()
                    Toast.makeText(
                        context,
                        getString(R.string.text_no_network_connection),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    progressDialogLoading.dismiss()
                    when (response.code()) {
                        HttpsURLConnection.HTTP_OK -> {
                            if (response.body() != null) {
                                loginUser = response.body()!!
                            }

                            saveLoginUserData()

                            loginApp()

                            Toast.makeText(
                                context,
                                getString(R.string.text_sign_in_success),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                            Toast.makeText(
                                context,
                                getString(R.string.text_unauthorized),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                context,
                                getString(R.string.text_error_occurred),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            })
        }
    }

    private fun handleTextViewRegisterNowListener() {
        tvRegisterNowW10?.setOnClickListener {
            openSignUpFragment()
        }
    }

    private fun changeStatusForSignInButton() {
        if (btnSignInW10?.isEnabled!!) {
            btnSignInW10?.setBackgroundResource(R.drawable.bg_button_enable)
        } else {
            btnSignInW10?.setBackgroundResource(R.drawable.bg_button_unable)
        }
    }

    private fun openSignUpFragment() {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.add(R.id.fragmentContainer, SignUpFragment().apply {
            onRegisterSuccess = { userRegister ->
                this@SignInFragment.edtEmailIdFragmentSignInW10.setText(userRegister.email)
                this@SignInFragment.edtPasswordFragmentSignInW10.setText(userRegister.password)
            }
        })
            ?.addToBackStack(null)
            ?.hide(this)
            ?.commit()
    }

    private fun loginApp() {
        val timeLineActivityIntent = Intent(this.context, TimeLineActivity::class.java)
        startActivity(timeLineActivityIntent)
        this.activity?.finish()
    }

    private fun saveLoginUserData() {
        val sharePref =
            context?.getSharedPreferences(USER_DATA_PREFS_WEEK_10, Context.MODE_PRIVATE)
        val editor = sharePref?.edit()
        editor?.putInt(ID_KEY, loginUser.id)
        editor?.putString(EMAIL_KEY, loginUser.email)
        editor?.putString(FULL_NAME_KEY, loginUser.fullName)
        editor?.putString(TOKEN_KEY, loginUser.token)
        editor?.apply()
    }
}
