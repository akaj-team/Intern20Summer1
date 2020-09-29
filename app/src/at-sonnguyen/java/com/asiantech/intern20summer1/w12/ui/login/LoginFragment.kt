package com.asiantech.intern20summer1.w12.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.data.source.repository.LoginRepository
import com.asiantech.intern20summer1.w12.extension.makeToastError
import com.asiantech.intern20summer1.w12.ui.activity.LoginActivity
import com.asiantech.intern20summer1.w12.ui.home.HomActivity
import com.asiantech.intern20summer1.w12.ui.register.RegisterFragment
import com.asiantech.intern20summer1.w12.ui.register.RegisterFragment.Companion.KEY_VALUE_EMAIL
import com.asiantech.intern20summer1.w12.ui.register.RegisterFragment.Companion.KEY_VALUE_PASSWORD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_login.*

class LoginFragment : Fragment() {
    private var viewModel: LoginVMContact? = null

    companion object {

        internal const val USER_KEY_LOGIN = "user"
        internal fun newInstance(email: String, password: String) = LoginFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_VALUE_EMAIL, email)
                putString(KEY_VALUE_PASSWORD, password)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LoginViewModel(LoginRepository())
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
        setEnableSignInButton()
        initListener()
    }

    private fun initListener() {
        handleEmailEditTextListener()
        handlePasswordEditTextListener()
        handleRegisterTextViewListener()
        handleLoginButtonListener()
    }

    private fun handleRegisterTextViewListener() {
        tvRegister.setOnClickListener {
            (activity as? LoginActivity)?.replaceFragment(RegisterFragment.newInstance())
        }
    }

    private fun getData() {
        arguments?.let {
            edtEmailLogin.setText(it.getString(KEY_VALUE_EMAIL))
            edtPasswordLogin.setText(it.getString(KEY_VALUE_PASSWORD))
        }
    }

    private fun handleEmailEditTextListener() {
        edtEmailLogin.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            viewModel?.isValidInformation(p0.toString(),edtPasswordLogin.text.toString())
        })
    }

    private fun handlePasswordEditTextListener() {
        edtPasswordLogin.addTextChangedListener(onTextChanged = {p0: CharSequence?, _, _, _ ->
            viewModel?.isValidInformation(edtEmailLogin.text.toString(),p0.toString())
        })
    }

    private fun setEnableSignInButton() {
        viewModel?.isValidInformationStatus()?.subscribe({
            btnLogin.isEnabled = it
        }, {
            //No-op
        })
    }

    private fun handleLoginButtonListener() {
        btnLogin?.setOnClickListener {
            progressBarLogin.visibility = View.VISIBLE
            viewModel?.login(edtEmailLogin.text.toString(), edtPasswordLogin.text.toString())?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        it.body()?.let { user ->
                            val intent = Intent(activity, HomActivity::class.java)
                            intent.putExtra(USER_KEY_LOGIN, user)
                            activity?.startActivity(intent)
                            progressBarLogin.visibility = View.INVISIBLE
                            activity?.finish()
                        }
                    } else {
                        makeToastError(it.code(), requireContext())
                        progressBarLogin.visibility = View.INVISIBLE
                    }
                }, {
                    // no ops
                })
        }
    }
}
