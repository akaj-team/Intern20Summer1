package fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import activity.SignInActivity
import extension.isValidEmail
import extension.isValidPasswordW4
import extension.textChangedListener
import kotlinx.android.synthetic.`at-vuongphan`.fragment_sign_in.*
import java.util.regex.Pattern

class FragmentSignIn : Fragment() {
    companion object {
        internal const val HINT = "Please Input "
        internal const val TITLE_DIALOG = "Title Sign In"
        internal const val MESSAGE = "Fail username or password"
        val pattern: Pattern = Pattern.compile("^(?=.*[0-9]).{8,16}\$")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openSignUpFragment()
        initEnableButtonLoginEmail()
        initEnableButtonLoginPassword()
        initPlaceHolder(edtEmail_w4)
        initPlaceHolder(edtPassword_w4)
    }

    private fun openSignUpFragment() {
        tvRegisterNow_w4.setOnClickListener {
            (activity as? SignInActivity)?.openSignUp()
        }
    }

    private fun isCorrectFormat(email: String, password: String) =
        email.isValidEmail() && password.isValidPasswordW4()

    private fun initEnableButtonLoginEmail() {
        edtEmail_w4.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin_w4.isEnabled = isCorrectFormat(p0.toString(), edtPassword_w4.text.toString())
        })
    }

    private fun initEnableButtonLoginPassword() {
        edtPassword_w4.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin_w4.isEnabled = isCorrectFormat(edtEmail_w4.text.toString(), p0.toString())
        })
    }

    private fun initPlaceHolder(edit: EditText) {
        edit.hint = HINT
    }

    private fun dialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(TITLE_DIALOG)
        builder.setMessage(MESSAGE)
        builder.setPositiveButton("Exit") { _: DialogInterface, _: Int -> }
        builder.show()
    }
}
