package com.asiantech.intern20summer1.w12.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.activity.HomeActivity
import com.asiantech.intern20summer1.w12.activity.LoginActivity
import com.asiantech.intern20summer1.w12.activity.LoginActivity.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.w12.activity.LoginActivity.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.asiantech.intern20summer1.w12.extension.isValidEmail
import com.asiantech.intern20summer1.w12.extension.isValidPassword
import com.asiantech.intern20summer1.w12.extension.makeToastError
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.ui.register.RegisterFragment
import com.asiantech.intern20summer1.w12.ui.register.RegisterFragment.Companion.KEY_VALUE_EMAIL
import com.asiantech.intern20summer1.w12.ui.register.RegisterFragment.Companion.KEY_VALUE_PASSWORD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_login.*

class LoginFragment : Fragment() {
    private var emailText: String = ""
    private var passwordText: String = ""
    private var viewModel : LoginVMContact? = null

    companion object {

        internal const val USER_KEY_LOGIN = "user"
        internal fun newInstance(email : String,password : String) = LoginFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_VALUE_EMAIL,email)
                putString(KEY_VALUE_PASSWORD,password)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LoginViewModel(RemoteRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w12_fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
//        getDataFromRegisterFragment()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        setEnableSignInButton()
    }

    private fun initListener() {
        handleEmailEditTextListener()
        handlePasswordEditTextListener()
        setEnableSignInButton()
        handleRegisterTextViewListener()
        handleLoginButtonListener()
    }

    private fun handleRegisterTextViewListener() {
        tvRegister.setOnClickListener {
            (activity as? LoginActivity)?.replaceFragment(RegisterFragment.newInstance())
        }
    }

    private fun getData(){
        arguments?.let {
            edtEmailLogin.setText(it.getString(KEY_VALUE_EMAIL))
            edtPasswordLogin.setText(it.getString(KEY_VALUE_PASSWORD))
        }
    }

    private fun getDataFromRegisterFragment() {
        (arguments?.getString(KEY_VALUE_EMAIL))?.let {
            edtEmailLogin.setText(it)
        }
        (arguments?.getString(KEY_VALUE_PASSWORD))?.let {
            edtPasswordLogin.setText(it)
        }
    }

    private fun handleEmailEditTextListener() {
        edtEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (isValidEmail(p0.toString())) {
                    edtEmailLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    emailText = p0.toString()
                } else {
                    edtEmailLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    emailText = ""
                }
            }
        })
    }

    private fun handlePasswordEditTextListener() {
        edtPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(p0.toString())) {
                    edtPasswordLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    passwordText = p0.toString()
                    setEnableSignInButton()
                } else {
                    edtPasswordLogin.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    passwordText = ""
                    setEnableSignInButton()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun setEnableSignInButton() {
        btnLogin.isEnabled =
            isValidEmail(edtEmailLogin.text.toString()) && isValidPassword(edtPasswordLogin.text.toString())
    }

    private fun handleLoginButtonListener() {
        btnLogin?.setOnClickListener {
            viewModel?.login(emailText, passwordText)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        it.body()?.let { user ->
                            val sharedPreferences = activity?.getSharedPreferences(
                                SHARED_PREFERENCE_FILE,
                                Context.MODE_PRIVATE
                            )
                            val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                            editor?.putString(SHARED_PREFERENCE_TOKEN_KEY, user.token)
                            editor?.apply()
                            val intent = Intent(activity, HomeActivity::class.java)
                            intent.putExtra(USER_KEY_LOGIN, user)
                            activity?.startActivity(intent)
                            activity?.finish()
                        }
                    } else {
                        makeToastError(it.code(),requireContext())
                    }
                }, {
                    // no ops
                    Log.d("TAG0000", "handleLoginButtonListener: $it")
                })
        }
    }
}
