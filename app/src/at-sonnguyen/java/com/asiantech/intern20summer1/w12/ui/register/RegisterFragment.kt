package com.asiantech.intern20summer1.w12.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import com.asiantech.intern20summer1.w12.data.source.repository.RegisterRepository
import com.asiantech.intern20summer1.w12.ui.login.LoginFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_register.*

class RegisterFragment : Fragment() {
    //    private var emailText = ""
//    private var fullNameText = ""
//    private var passwordText = ""
//    private var checkFullName = false
    private var viewModel: RegisterVMContact? = null

    companion object {
        internal fun newInstance() = RegisterFragment()
        internal const val KEY_VALUE_EMAIL = "email_key"
        internal const val KEY_VALUE_PASSWORD = "password_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = RegisterViewModel(RegisterRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w12_fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        handleEmailEditTextListener()
        handleFullNameEditTextListener()
        handlePasswordEditTextListener()
        setEnableRegisterButton()
        handleRegisterButtonListener()
    }

    private fun setEnableRegisterButton() {
        viewModel?.isValidInfoStatus()?.subscribe({
            btnRegister.isEnabled = it
        }, {
            //No-op
        })
    }

    private fun handleEmailEditTextListener() {
        edtEmailRegister.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            viewModel?.isValidInformation(
                p0.toString(),
                edtFullNameRegister.text.toString(),
                edtPasswordRegister.text.toString()
            )
        })
    }

    private fun handleRegisterButtonListener() {
        btnRegister.setOnClickListener {
            progressBarRegister.visibility = View.VISIBLE
            viewModel?.register(
                UserRegister(
                    edtEmailRegister.text.toString(),
                    edtPasswordRegister.text.toString(),
                    edtFullNameRegister.text.toString()
                )
            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            getString(R.string.w12_register_success_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                        sendDataToLoginFragment()
                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.w12_register_failure_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressBarRegister.visibility = View.INVISIBLE
                }, {
                    // No-ops
                })
        }
    }

    private fun sendDataToLoginFragment() {
        val loginFragment = LoginFragment.newInstance(
            edtEmailRegister.text.toString(),
            edtPasswordRegister.text.toString()
        )
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction?.replace(R.id.frameLayoutMain, loginFragment)
        fragmentManager?.popBackStack()
        fragmentTransaction?.commit()
    }

    private fun handlePasswordEditTextListener() {
        edtPasswordRegister.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            viewModel?.isValidInformation(
                edtEmailRegister.text.toString(),
                edtFullNameRegister.text.toString(),
                p0.toString()
            )
        })
    }

    private fun handleFullNameEditTextListener() {
        edtFullNameRegister.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            viewModel?.isValidInformation(
                edtEmailRegister.text.toString(),
                p0.toString(),
                edtPasswordRegister.text.toString()
            )
        })
    }
}
