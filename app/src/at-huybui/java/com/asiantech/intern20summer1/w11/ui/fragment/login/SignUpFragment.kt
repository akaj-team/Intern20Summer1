package com.asiantech.intern20summer1.w11.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import com.asiantech.intern20summer1.w11.data.source.LocalRepository
import com.asiantech.intern20summer1.w11.data.source.LoginRepository
import com.asiantech.intern20summer1.w11.data.source.remote.network.ApiClient
import com.asiantech.intern20summer1.w11.data.source.remote.network.ErrorUtils
import com.asiantech.intern20summer1.w11.utils.extension.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_up.*
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is SignInFragment class. It is fragment to display sign up page
 */

class SignUpFragment : Fragment() {

    companion object {
        private const val MAX_LENGTH_EMAIL = 65
        private const val REGEX_PASSWORD = """^(?=.*[0-9])(?=.*[a-z]).{8,16}$"""
        internal fun newInstance() = SignUpFragment()
    }

    internal var onRegisterClick: (account: RequestAccount) -> Unit = {}

    private var viewModel: LoginVM? = null
    private var requestAccount = RequestAccount()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        return inflater.inflate(R.layout.w10_fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnBack?.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        btnRegister?.setOnClickListener {
            registerAccount()
        }

        handleForEditText()
    }

    private fun handleForEditText() {
        edtEmail?.addTextChangedListener {
            checkEditText()
        }
        edtPassword?.addTextChangedListener {
            checkEditText()
        }
        edtRePass?.addTextChangedListener {
            checkEditText()
        }
    }

    private fun checkEditText() {
        if (isCheckEmail() && isCheckName() && isCheckPassword()) {
            btnRegister?.setBackgroundResource(R.drawable.w10_bg_select_button)
            btnRegister?.isEnabled = true
        } else {
            btnRegister?.setBackgroundResource(R.drawable.w10_bg_button_disable)
            btnRegister?.isEnabled = false
        }
    }

    private fun isCheckEmail() =
        PatternsCompat.EMAIL_ADDRESS.matcher(edtEmail?.text.toString()).matches()

    private fun isCheckPassword(): Boolean {
        val isCheck = edtPassword.text.toString().matches(REGEX_PASSWORD.toRegex())
        val isMatch = edtPassword.text.toString() == edtRePass.text.toString()
        return isCheck && isMatch
    }

    private fun isCheckName(): Boolean {
        val name = edtFullName.text.toString()
        return (name.isNotEmpty()) && (name.length < MAX_LENGTH_EMAIL)
    }

    private fun registerAccount() {
        progressBar?.visibility = View.VISIBLE
        val email = edtEmail.text.toString()
        val fullName = edtFullName.text.toString()
        val password = edtPassword.text.toString()
        requestAccount = RequestAccount(email, password, fullName)

        viewModel
            ?.createUser(requestAccount)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(this::handleOnSuccess, this::handleOnError)
    }

    private fun handleOnSuccess(response: Response<Account>) {
        if (response.isSuccessful) {
            getString(R.string.w10_register_complete).showToast(requireContext())
            response.body()?.let { onRegisterClick.invoke(requestAccount) }
            fragmentManager?.popBackStack()
        } else {
            val error = ErrorUtils().parseError(response)
            if (error?.message ==
                ApiClient.MESSAGE_EMAIL_HAS_BEEN_TAKEN
            ) {
                getString(R.string.w10_email_is_been_take).showToast(requireContext())
            }
        }
        progressBar?.visibility = View.INVISIBLE
    }

    private fun handleOnError(t: Throwable) {
        t.message.toString().showToast(requireContext())
    }

    private fun setupViewModel() {
        viewModel = LoginVM(LoginRepository(requireContext()), LocalRepository(requireContext()))
    }
}
