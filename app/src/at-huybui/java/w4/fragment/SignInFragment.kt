package w4.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.fragment_sign_in.*

@Suppress("DEPRECATION")
class SignInFragment : Fragment() {

    companion object {
        val REGEX_PASSWORD = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$""".toRegex()
    }

    private var emailBuffer = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvRegisterNow.setOnClickListener {
            handleOpenSignUpFragment()
        }

        handleForEmailEditText()
    }

    private fun handleOpenSignUpFragment() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.llMain,
            SignUpFragment()
        )
        fragmentTransaction?.hide(this)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun handleForEmailEditText() {
        edtSignInEmail.addTextChangedListener { text ->
            val pattern = PatternsCompat.EMAIL_ADDRESS.matcher(text.toString()).matches()
            setIconForEditText(edtSignInEmail, pattern)
            emailBuffer = if (pattern) text.toString() else ""
        }
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
