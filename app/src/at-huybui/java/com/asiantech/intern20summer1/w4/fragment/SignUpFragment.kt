package com.asiantech.intern20summer1.w4.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.classanother.Account
import com.asiantech.intern20summer1.w4.fragment.SignInFragment.Companion.REGEX_PASSWORD
import kotlinx.android.synthetic.`at-huybui`.fragment_sign_up.*


class SignUpFragment : Fragment() {
    companion object {
        private const val SDK_VERSION = 16
        private const val REGEX_NUMBER_PHONE = """^[0-9]{10}$"""
        private const val TICK_ICON: Int = 1
        private const val ERROR_ICON: Int = 0
        private const val HIDE_ICON: Int = -1
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 101
        private const val PERMISSION_REQUEST_CODE = 200
        private const val CROP_PIC = 102
        internal fun newInstance() = SignUpFragment()
    }

    private var imageUri: Uri? = null
    private var toastStatus: Toast? = null
    private var emailBuffer = ""
    private var nameBuffer = ""
    private var numberPhoneBuffer = ""
    private var passwordBuffer = ""
    private var rewritePassStatus = false
    private var isCheckCamera = false
    private var isCheckGallery = false

    internal var onRegisterClick: (Account) -> Unit = { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleForBackToSignInButton()
        handleForEmailEditText()
        handleForPasswordEditText()
        handleForNumberPhoneEditText()
        handleForRewritePasswordEditText()
        handleForRegisterButton()
        handleForFullNameEditText()
        handleForAvatarImage()
    }

    override fun onResume() {
        super.onResume()
        handleCheckPermissionAfterRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            performCrop()
        } else if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == RESULT_OK) {
            imageUri = data?.data
            performCrop()
        } else if (requestCode == CROP_PIC && resultCode == RESULT_OK) {
            showToast(getString(R.string.crop_complete))
            imgAvatarSignUp.setImageURI(imageUri)
        }
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
                setIconForEditText(
                    edtNameSingUp,
                    TICK_ICON
                )
                text.toString()
            } else {
                setIconForEditText(
                    edtNameSingUp,
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
                    setIconForEditText(
                        edtEmailSignUp,
                        TICK_ICON
                    )
                    text.toString()
                } else {
                    setIconForEditText(
                        edtEmailSignUp,
                        ERROR_ICON
                    )
                    ""
                }
            } else {
                setIconForEditText(
                    edtEmailSignUp,
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
            val pattern = text.toString().matches(REGEX_NUMBER_PHONE.toRegex())
            numberPhoneBuffer = if (text.toString().isNotEmpty()) {
                if (pattern) {
                    setIconForEditText(
                        edtNumberSignUp,
                        TICK_ICON
                    )
                    text.toString()
                } else {
                    setIconForEditText(
                        edtNumberSignUp,
                        ERROR_ICON
                    )
                    ""
                }
            } else {
                setIconForEditText(
                    edtNumberSignUp,
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
            val pattern = text.toString().matches(REGEX_PASSWORD.toRegex())
            passwordBuffer = if (text.toString().isNotEmpty()) {
                if (pattern) {
                    setIconForEditText(
                        edtPasswordSignUp,
                        TICK_ICON
                    )
                    text.toString()
                } else {
                    setIconForEditText(
                        edtPasswordSignUp,
                        ERROR_ICON
                    )
                    ""
                }
            } else {
                setIconForEditText(
                    edtPasswordSignUp,
                    HIDE_ICON
                )
                ""
            }
            recheckForReWritePasswordEditText()
        }
    }

    /**
     * This function is rechecking fof rewrite password edittext
     */
    private fun recheckForReWritePasswordEditText() {
        //job of handle function for rewrite password edit text view
        rewritePassStatus = if (edtRePassSignUp.text.toString().isNotEmpty()) {
            if (edtRePassSignUp.text.toString() == passwordBuffer) {
                setIconForEditText(
                    edtRePassSignUp,
                    TICK_ICON
                )
                true
            } else {
                setIconForEditText(
                    edtRePassSignUp,
                    ERROR_ICON
                )
                false
            }
        } else {
            setIconForEditText(
                edtRePassSignUp,
                HIDE_ICON
            )
            false
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
                    setIconForEditText(
                        edtRePassSignUp,
                        TICK_ICON
                    )
                    true
                } else {
                    setIconForEditText(
                        edtRePassSignUp,
                        ERROR_ICON
                    )
                    false
                }
            } else {
                setIconForEditText(
                    edtRePassSignUp,
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
            when {
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
                    showToast(getString(R.string.enter_input_your_password))
                }
                edtRePassSignUp.text.isEmpty() -> {
                    edtRePassSignUp.requestFocus()
                    showToast(getString(R.string.please_rewrite_your_password))
                }
                emailBuffer.isEmpty() -> {
                    edtEmailSignUp.requestFocus()
                    showToast(getString(R.string.invalid_email))
                }
                numberPhoneBuffer.isEmpty() -> {
                    edtNumberSignUp.requestFocus()
                    showToast(getString(R.string.invalid_number_phone))
                }
                passwordBuffer.isEmpty() -> {
                    edtPasswordSignUp.requestFocus()
                    showToast(getString(R.string.invalid_password))
                }
                !rewritePassStatus -> {
                    edtRePassSignUp.requestFocus()
                    showToast(getString(R.string.password_does_not_match))
                }
                else -> {
                    showToast(getString(R.string.register_complete))
                    val use =
                        Account(
                            nameBuffer,
                            emailBuffer,
                            numberPhoneBuffer,
                            passwordBuffer,
                            imageUri.toString() + ""
                        )
                    onRegisterClick(use)
                    fragmentManager?.popBackStack()
                }
            }
        }
    }

    /**
     * Handle function for avatar image view
     */
    private fun handleForAvatarImage() {
        imgAvatarSignUp.setOnClickListener {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle(getString(R.string.select))
            val select = arrayOf(getString(R.string.camera), getString(R.string.gallery))
            builder?.setItems(select) { _, which ->

                when (which) {
                    0 -> {
                        if (isCheckCameraPermission()) {
                            openCamera()
                        } else {
                            isCheckCamera = true
                            requestCameraPermission()
                        }
                    }
                    1 -> {
                        if (isCheckGalleryPermission()) {
                            openGallery()
                        } else {
                            isCheckGallery = true
                            requestGalleryPermission()
                        }
                    }
                }
            }
            val dialog = builder?.create()
            dialog?.show()
        }
    }

    /**
     * This function set icon for edit text views
     * If argument is TICK_ICON, set tick icon for edit text view
     * If argument is ERROR_ICON, set error icon for edit text view
     * If argument is HIDE_ICON, hide icon of edit text view
     * Argument had defined in companion
     */
    private fun setIconForEditText(editText: EditText, status: Int) {
        if (Build.VERSION.SDK_INT > SDK_VERSION) {
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
    }

    /**
     * This check permission enter camera of application
     */
    private fun isCheckCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) && ((ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED))
    }

    /**
     * This function request permission enter to cameraE
     */
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    /**
     * This function capture a image from camera
     */
    private fun openCamera() {
        val values = ContentValues()
        imageUri =
            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    /**
     * This check permission enter gallery of application
     */
    private fun isCheckGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * This function request permission enter to gallery
     */
    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    /**
     * This function take a image from gallery
     */
    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = "image/*"
        startActivityForResult(intentGallery, REQUEST_SELECT_IMAGE_IN_ALBUM)
    }

    /**
     * This function check permission of camera and store after request to open camera or gallery now
     */
    private fun handleCheckPermissionAfterRequest() {
        when {
            isCheckCamera -> {
                isCheckCamera = false
                if (isCheckCameraPermission()) {
                    openCamera()
                }
            }
            isCheckGallery -> {
                isCheckGallery = false
                if (isCheckGalleryPermission()) {
                    openGallery()
                }
            }
        }
    }

    /**
     * This function will show a toast on display
     */
    private fun showToast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        toastStatus?.cancel()
        toastStatus = Toast.makeText(context, text.toString(), duration).apply { show() }
    }


    /**
     *
     */
    private fun performCrop() {
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            val cropIntent = Intent("com.android.camera.action.CROP")
            // indicate image type and Uri
            cropIntent.setDataAndType(imageUri, "image/*")
            // set crop properties
            cropIntent.putExtra("crop", "true")
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256)
            cropIntent.putExtra("outputY", 256)
            // retrieve data on return
            cropIntent.putExtra("return-data", true)
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC)
        } // respond to users whose devices do not support the crop action
        catch (anfe: ActivityNotFoundException) {
            showToast("This device doesn't support the crop action!")
        }
    }
}
