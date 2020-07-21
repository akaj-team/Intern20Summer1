package com.asiantech.intern20summer1

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.`at-hoangtran`.w4_sign_up_fragment.*
import android.os.Bundle as Bundle

class W4SignUpFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w4_sign_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEditText(edt_email)
        handleEditText(edt_confirm_password)
    }

    private fun handleEditText(edt: EditText) {
        var emailCheck: Boolean = false
        var passCheck: Boolean = false
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = edt.toString()
                when (edt) {
                    edt_email -> {
                        if (Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                            emailCheck = true
                        }
                    }
                    edt_confirm_password -> {
                        if (str.toString() == edt_password.toString()) {
                            passCheck = true
                        }
                    }
                }
            }
        })
        if (emailCheck && passCheck) {
            btn_register.isEnabled = true
        }
    }
    private fun handleBtnRegister(){
        btn_register.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Email", edt_email.text.toString())
        }
    }
}
