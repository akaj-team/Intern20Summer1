package com.asiantech.intern20summer1.week4.fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.extensions.hideSoftKeyboard
import com.asiantech.intern20summer1.week4.models.User
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-linhle`.fragment_signup.*
import java.io.*
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    // Interface to pass data
    internal var onRegisterSuccess: (user: User) -> Unit = {}
    private val user = User("", "", "", "", "")

    // At least 1 digit
    private val passwordPattern = Pattern.compile("""^(?=.*[0-9]).{8,16}$""")

    // Must have 10 digits
    private val phonePattern = Pattern.compile("""^([0-9]){10}$""")
    private var path: String? = ""

    companion object {
        private const val KEY_IMAGE = "data"
        private const val OPEN_CAMERA_REQUEST = 1
        private const val PICK_IMAGE_REQUEST = 2
        private const val KEY_IMAGE_GALLERY = "image/*"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingArrowLeft()
        handleSignUpFullNameTextChanged()
        handleSignUpPhoneTextChanged()
        handleSignUpEmailTextChanged()
        handleSignUpPasswordTextChanged()
        handleSignUpConfirmPasswordTextChanged()
        handleOnTouchScreen()
        handleClickingRegisterButton()
        handleClickingArrowLeft()
        handleListener()
    }

    private fun handleClickingArrowLeft() {
        imgArrowLeft.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun isSignUpFullNameValid(fullName: String) = fullName != ""

    private fun isSignUpPhoneValid(phone: String) = phonePattern.matcher(phone).matches()

    private fun isSignUpPasswordValid(password: String) =
        passwordPattern.matcher(password).matches()

    private fun isSignUpEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isSignUpConfirmPassword(confirmPassword: String) =
        passwordPattern.matcher(confirmPassword).matches()
                && confirmPassword == edtSignUpPassword.text.toString()

    @SuppressLint("ClickableViewAccessibility")
    private fun handleOnTouchScreen() {
        llSignUpMain?.setOnTouchListener { it, _ ->
            it.clearFocus()
            it.hideSoftKeyboard()
            true
        }
    }

    // Check all edit text correct validate
    private fun isCorrectFormat(
        fullName: String,
        phone: String,
        password: String,
        email: String,
        confirmPassword: String
    ) = isSignUpFullNameValid(fullName) && isSignUpPhoneValid(phone)
            && isSignUpEmailValid(email) && isSignUpPasswordValid(password)
            && isSignUpConfirmPassword(confirmPassword)

    private fun handleSignUpFullNameTextChanged() {
        edtSignUpFullName.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                p0.toString(),
                edtSignUpPhone.text.toString(),
                edtSignUpPassword.text.toString(),
                edtSignUpEmail.text.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpPhoneTextChanged() {
        edtSignUpPhone.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                p0.toString(),
                edtSignUpPassword.text.toString(),
                edtSignUpEmail.text.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpEmailTextChanged() {
        edtSignUpEmail.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                edtSignUpPhone.text.toString(),
                edtSignUpPassword.text.toString(),
                p0.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpPasswordTextChanged() {
        edtSignUpPassword.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                edtSignUpPhone.text.toString(),
                p0.toString(),
                edtSignUpEmail.text.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpConfirmPasswordTextChanged() {
        edtSignUpConfirmPassword.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                edtSignUpPhone.text.toString(),
                edtSignUpPassword.text.toString(),
                edtSignUpEmail.text.toString(),
                p0.toString()
            )
        })
    }

    // Pass data when click button register
    private fun handleClickingRegisterButton() {
        btnRegister.setOnClickListener {
            user.avatar = path
            user.email = edtSignUpEmail.text.toString()
            user.fullName = edtSignUpFullName.text.toString()
            user.password = edtSignUpPassword.text.toString()
            user.phone = edtSignUpPhone.text.toString()
            onRegisterSuccess(user)
            fragmentManager?.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                OPEN_CAMERA_REQUEST -> {
                    cropImageCamera(data)
                }
                PICK_IMAGE_REQUEST -> {
                    cropImageGallery(data)
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    showImage(data)
                }
            }
        }
    }

    private fun cropImageCamera(data: Intent?) {
        (data?.extras?.get(KEY_IMAGE) as? Bitmap)?.let {
            getImageUri(it)?.let { uri -> handleCropImage(uri) }
        }
    }

    private fun cropImageGallery(data: Intent?) {
        data?.data.let {
            it?.let { it1 -> handleCropImage(it1) }
        }
    }

    private fun showImage(data: Intent?) {
        CropImage.getActivityResult(data).uri.apply {
            if (this != null) {
                val bitmap = Images.Media.getBitmap(
                    activity?.contentResolver,
                    this
                )
                profile_image.setImageBitmap(bitmap)
            }
        }
        path = saveToInternalStorage((profile_image.drawable as BitmapDrawable).bitmap)
    }

    private fun handleCropImage(uri: Uri) {
        context?.let { context ->
            CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .start(context, this)
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            Images.Media.insertImage(context?.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun handleListener() {
        profile_image.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Choose an option:")
            val optionList = arrayOf("Gallery", "Camera")
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        val intentImage =
                            Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI)
                        intentImage.type = KEY_IMAGE_GALLERY
                        startActivityForResult(intentImage, PICK_IMAGE_REQUEST)
                    }
                    1 -> {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST)
                    }
                }
            }
            // Create and show the alert dialog
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    // Save image to internal storage
    private fun saveToInternalStorage(bitmapImage: Bitmap): String? {
        val cw = ContextWrapper(context)
        // path to /data/data/yourApp/app_data/imageDir
        val directory = cw.getDir("image", Context.MODE_PRIVATE)
        // Create imageDir
        val myPath = File(directory, "profile.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }
}
