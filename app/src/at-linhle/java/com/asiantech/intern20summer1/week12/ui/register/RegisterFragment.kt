package com.asiantech.intern20summer1.week12.ui.register

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import com.asiantech.intern20summer1.week12.data.source.LoginRepository
import com.asiantech.intern20summer1.week12.extensions.handleOnTouchScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-linhle`.fragment_register.*

class RegisterFragment : Fragment() {

    companion object {
        private const val RESPONSE_CODE = 400
        internal fun newInstance() = RegisterFragment()
    }

    internal var onRegisterSuccess: (email: String, password: String) -> Unit = { _, _ -> }
    private var viewModel: RegisterMVContract? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = RegisterViewModel(LoginRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingArrowBack()
        handleRegisterEmailTextChanged()
        handleRegisterFullNameTextChanged()
        handleRegisterPasswordTextChanged()
        handleClickingRegisterButton()
        handleOnTouchScreen(llRegisterMain)
    }


    private fun handleRegisterFullNameTextChanged() {
        edtUserName?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            viewModel?.validateRegisterInformation(
                p0.toString(),
                edtEmail?.text.toString(),
                edtPassword?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterEmailTextChanged() {
        edtEmail?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            viewModel?.validateRegisterInformation(
                edtUserName?.text.toString(),
                p0.toString(),
                edtPassword?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterPasswordTextChanged() {
        edtPassword?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            viewModel?.validateRegisterInformation(
                edtUserName?.text.toString(),
                edtEmail?.text.toString(),
                p0.toString()
            )
            setBackgroundButton()
        })
    }

    private fun setBackgroundButton() {
        viewModel?.infoValidateStatus()?.subscribe({
            btnRegister?.isEnabled = it
            if (it) {
                btnRegister?.setBackgroundResource(R.drawable.bg_button_enabled)
            } else {
                btnRegister?.setBackgroundResource(R.drawable.bg_button_disabled)
            }
        }, {
            //No-op
        })
    }

    private fun handleClickingArrowBack() {
        imgArrowLeft?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun handleClickingRegisterButton() {
        btnRegister?.setOnClickListener {
            progressDialog.show()
            val fullName = edtUserName?.text.toString()
            val email = edtEmail?.text.toString()
            val password = edtPassword?.text.toString()
            viewModel?.register(UserRegister(email, password, fullName))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            getString(R.string.register_fragment_register_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        onRegisterSuccess(email, password)
                        progressDialog.dismiss()
                        activity?.onBackPressed()
                    } else {
                        progressDialog.dismiss()
                        if (it.code() == RESPONSE_CODE) {
                            Toast.makeText(
                                activity,
                                getString(R.string.register_fragment_email_already_have),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                activity,
                                getString(R.string.register_fragment_register_fail),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }, {
                    //No-op
                })
        }
    }
}
