package com.asiantech.intern20summer1.w4.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.classanother.Account
import com.asiantech.intern20summer1.w4.fragment.W4_SignInFragment.Companion.REGEX_PASSWORD
import kotlinx.android.synthetic.`at-huybui`.fragment_sign_up.*

class W4_SignUpFragment : Fragment() {
    companion object {
        private val REGEX_NUMBER_PHONE = """^[0-9]{10}$""".toRegex()
        private const val TICK_ICON: Int = 1
        private const val ERROR_ICON: Int = 0
        private const val HIDE_ICON: Int = -1
    }

    private var toastStatus: Toast? = null
    private var emailBuffer = ""
    private var nameBuffer = ""
    private var numberPhoneBuffer = ""
    private var passwordBuffer = ""
    private var rewritePassStatus = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleForBackToSignInButton()
        handleForEmailEditText()
        handleForPasswordEditText()
        handleForNumberPhoneEditText()
        handleForRewritePasswordEditText()
        handleForRegisterButton()
        handleForFullNameEditText()
        handleForAvatarImage()
    }

    /**
     * This function handle for back to sign in button
     * Click back to sign in button well back to sign in fragment
     */
    private fun handleForBackToSignInButton() {
        btnBackToSignIn.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    /**
     * This function will handle for name edit text
     * It will show or hide tick icon follow text
     */
    private fun handleForFullNameEditText() {
        edtNameSingUp.addTextChangedListener { text ->
            nameBuffer = if (edtNameSingUp.text.isNotEmpty()) {
                setIconForEditText(edtNameSingUp,
                    TICK_ICON
                )
                text.toString()
            } else {
                setIconForEditText(edtNameSingUp,
                    HIDE_ICON
                )
                ""
            }
        }
    }

    /**
     * This function will handle for email edit text
     * It will change tick or error icon follow text in box
     */
    private fun handleForEmailEditText() {
        edtEmailSignUp.addTextChangedListener { text ->
            val pattern = PatternsCompat.EMAIL_ADDRESS.matcher(text.toString()).matches()
            emailBuffer = if (text.toString().isNotEmpty()) {
                if (pattern) {
                    setIconForEditText(edtEmailSignUp,
                        TICK_ICON
                    )
                    text.toString()
                } else {
                    setIconForEditText(edtEmailSignUp,
                        ERROR_ICON
                    )
                    ""
                }
            } else {
                setIconForEditText(edtEmailSignUp,
                    HIDE_ICON
                )
                ""
            }
        }
    }

    /**
     * This function will handle for number phone edit text
     * It will change tick or error icon follow text in box
     */
    private fun handleForNumberPhoneEditText() {
        edtNumberSignUp.addTextChangedListener { text ->
            val pattern = text.toString().matches(REGEX_NUMBER_PHONE)
            numberPhoneBuffer = if (text.toString().isNotEmpty()) {
                if (pattern) {
                    setIconForEditText(edtNumberSignUp,
                        TICK_ICON
                    )
                    text.toString()
                } else {
                    setIconForEditText(edtNumberSignUp,
                        ERROR_ICON
                    )
                    ""
                }
            } else {
                setIconForEditText(edtNumberSignUp,
                    HIDE_ICON
                )
                ""
            }
        }
    }

    /**
     * This function will handle for password edit text
     * It will change tick or error icon follow text in box
     * It also handle job of handle for rewrite password function
     */
    private fun handleForPasswordEditText() {
        edtPasswordSignUp.addTextChangedListener { text ->
            val pattern = text.toString().matches(REGEX_PASSWORD)
            passwordBuffer = if (text.toString().isNotEmpty()) {
                if (pattern) {
                    setIconForEditText(edtPasswordSignUp,
                        TICK_ICON
                    )
                    text.toString()
                } else {
                    setIconForEditText(edtPasswordSignUp,
                        ERROR_ICON
                    )
                    ""
                }
            } else {
                setIconForEditText(edtPasswordSignUp,
                    HIDE_ICON
                )
                ""
            }
            //job of handle function for rewrite password edit text view
            rewritePassStatus = if (edtRePassSignUp.text.toString().isNotEmpty()) {
                if (edtRePassSignUp.text.toString() == passwordBuffer) {
                    setIconForEditText(edtRePassSignUp,
                        TICK_ICON
                    )
                    true
                } else {
                    setIconForEditText(edtRePassSignUp,
                        ERROR_ICON
                    )
                    false
                }
            } else {
                setIconForEditText(edtRePassSignUp,
                    HIDE_ICON
                )
                false
            }
        }
    }

    /**
     * This function will handle for rewrite password edit text
     * It will change tick or error icon follow text in box
     * It will check match or not match follow password
     */
    private fun handleForRewritePasswordEditText() {
        edtRePassSignUp.addTextChangedListener { text ->
            rewritePassStatus = if (text.toString().isNotEmpty()) {
                if (text.toString() == passwordBuffer) {
                    setIconForEditText(edtRePassSignUp,
                        TICK_ICON
                    )
                    true
                } else {
                    setIconForEditText(edtRePassSignUp,
                        ERROR_ICON
                    )
                    false
                }
            } else {
                setIconForEditText(edtRePassSignUp,
                    HIDE_ICON
                )
                false
            }
        }
    }

    /**
     * This function handle for register button
     * It check edit text boxes and show toast follow condition
     */
    private fun handleForRegisterButton() {
        btnRegister.setOnClickListener {
            when (true) {
                edtNameSingUp.text.isEmpty() -> {
                    edtNameSingUp.requestFocus()
                    showToast(getString(R.string.enter_input_your_name))
                }
                edtEmailSignUp.text.isEmpty() -> {
                    edtEmailSignUp.requestFocus()
                    showToast(getString(R.string.enter_input_your_email))
                }
                edtNumberSignUp.text.isEmpty() -> {
                    edtNumberSignUp.requestFocus()
                    showToast(getString(R.string.enter_input_your_email))
                }
                edtPasswordSignUp.text.isEmpty() -> {
                    edtPasswordSignUp.requestFocus()
                    showToast("Enter input your password")
                }
                edtRePassSignUp.text.isEmpty() -> {
                    edtRePassSignUp.requestFocus()
                    showToast("Please, rewrite your password")
                }
                emailBuffer.isEmpty() -> {
                    edtEmailSignUp.requestFocus()
                    showToast("Invalid email")
                }
                numberPhoneBuffer.isEmpty() -> {
                    edtNumberSignUp.requestFocus()
                    showToast("Invalid number phone")
                }
                passwordBuffer.isEmpty() -> {
                    edtPasswordSignUp.requestFocus()
                    showToast("Invalid password")
                }
                !rewritePassStatus -> {
                    edtRePassSignUp.requestFocus()
                    showToast("Password does not match")
                }
                else -> {
                    showToast("Register complete!")
                    val account =
                        Account(
                            nameBuffer,
                            emailBuffer,
                            numberPhoneBuffer,
                            passwordBuffer
                        )
                }
            }
        }
    }

    /**
     * Handle function for avatar image view
     */
    private fun handleForAvatarImage() {
        imgAvatarSignUp.setOnClickListener {
         //   PickImageDialog.build()

//            val takeIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takeIntent, 0)
        }
    }

    /**
     * This function set icon for edit text views
     * If argument is TICK_ICON, set tick icon for edit text view
     * If argument is ERROR_ICON, set error icon for edit text view
     * If argument is HIDE_ICON, hide icon of edit text view
     * Argument had defined in companion
     */
    @SuppressLint("NewApi")
    private fun setIconForEditText(editText: EditText, status: Int) {
        when (status) {
            TICK_ICON -> editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.icon_tick,
                0
            )
            ERROR_ICON -> editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.icon_error,
                0
            )
            HIDE_ICON -> editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                0,
                0
            )
        }
    }

    /**
     * This function will show a toast on display
     */
    private fun showToast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        toastStatus?.cancel()
        toastStatus = Toast.makeText(context, text.toString(), duration).apply { show() }
    }
}
