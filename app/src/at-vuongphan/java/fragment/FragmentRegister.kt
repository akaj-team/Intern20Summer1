package fragment

import activity.SignInActivity
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.theartofdev.edmodo.cropper.CropImage
import data.User
import extension.*
import kotlinx.android.synthetic.`at-vuongphan`.fragment_sign_up.*
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class FragmentRegister : Fragment() {

    companion object {
        internal const val REQUEST_IMAGE_CAPTURE = 1
        internal const val REQUEST_GET_CONTENT_IMAGE = 2
        internal const val KEY_IMAGE = "data"
        internal const val TITLE_DIALOG_IMAGE = "Choose Avatar"
        internal const val IMAGE_CAMERA = "Camera"
        internal const val IMAGE_GALLERY = "Gallery"
        internal const val KEY_IMAGE_GALLERY = "image/*"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChooseImage()
        enableRegisterButton()
        sendDataLLoginFromRegister()
        initBackImageViewButton()
        initListenerHideKeyboardRegister()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    cropImageCamera(data)

                }
                REQUEST_GET_CONTENT_IMAGE -> {
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
                imgIconName.setImageBitmap(bitmap)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardRegister() {
        rlFragmentRegister?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun sendDataLLoginFromRegister() {
        btnRegister.setOnClickListener {
            (activity as? SignInActivity)?.openRegister(
                User(
                    edtFullName.text.toString(),
                    edtEmailSignUp.text.toString(),
                    edtNumber.text.toString(),
                    edtPasswordSignUp.text.toString()
                )
            )
        }
    }

    private fun initBackImageViewButton() {
        imgBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
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

    private fun initChooseImage() {
        imgIconName.setOnClickListener {
            showListAlertDialog()
        }
    }

    private fun showListAlertDialog() {
        AlertDialog.Builder(context).apply {
            setTitle(TITLE_DIALOG_IMAGE)
            val items = arrayOf(IMAGE_GALLERY, IMAGE_CAMERA)
            setItems(items) { _, which ->
                when (which) {
                    0 -> {
                        val intentImage =
                            Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI)
                        intentImage.type = KEY_IMAGE_GALLERY
                        startActivityForResult(intentImage, REQUEST_GET_CONTENT_IMAGE)
                    }
                    1 -> {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
            create().show()
        }
    }

    private fun isCorrectFormatSignUp(
        name: String,
        email: String,
        phone: String,
        password: String,
        typePassword: String
    ) = name.isFullName()
            && email.isValidEmail() && phone.isPhoneNumber() && password.isValidPasswordW4()
            && typePassword.isConfirmPassword(password)

    private fun enableRegisterButton() {
        edtFullName.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp(
                p0.toString(),
                edtEmailSignUp.text.toString(),
                edtNumber.text.toString(),
                edtPasswordSignUp.text.toString(),
                edtPasswordConfirm.text.toString()
            )
        })
        edtEmailSignUp.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp(
                edtFullName.text.toString(),
                p0.toString(),
                edtNumber.text.toString(),
                edtPasswordSignUp.text.toString(),
                edtPasswordConfirm.text.toString()
            )
        })
        edtNumber.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp(
                edtFullName.text.toString(),
                edtEmailSignUp.text.toString(),
                p0.toString(),
                edtPasswordSignUp.text.toString(),
                edtPasswordConfirm.text.toString()
            )
        })
        edtPasswordSignUp.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp(
                edtFullName.text.toString(),
                edtEmailSignUp.text.toString(),
                edtNumber.text.toString(),
                p0.toString(),
                edtPasswordConfirm.text.toString()
            )
        })
        edtPasswordConfirm.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp(
                edtFullName.text.toString(),
                edtEmailSignUp.text.toString(),
                edtNumber.text.toString(),
                edtPasswordSignUp.text.toString(),
                p0.toString()
            )
        })
    }
}
