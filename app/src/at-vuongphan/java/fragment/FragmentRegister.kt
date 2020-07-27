package fragment

import activity.SignInActivity
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.theartofdev.edmodo.cropper.CropImage
import data.User
import extension.*
import kotlinx.android.synthetic.`at-vuongphan`.fragment_sign_up.*
import java.io.ByteArrayOutputStream

@SuppressLint("WrongConstant")
@Suppress("DEPRECATION")
class FragmentRegister : Fragment() {
    var flag = false

    companion object {
        internal const val REQUEST_IMAGE_CAPTURE = 1
        internal const val REQUEST_GET_CONTENT_IMAGE = 2
        internal const val KEY_IMAGE = "data"
        internal const val TITLE_DIALOG_IMAGE = "Choose Avatar"
        internal const val IMAGE_CAMERA = "Camera"
        internal const val IMAGE_GALLERY = "Gallery"
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
                    if (!checkGalleryPermission()) {
                        requestGalleryPermission()
                    } else {
                        cropImageCamera(data)
                    }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                initPermissionCamera(grantResults)
            }
            REQUEST_GET_CONTENT_IMAGE -> {
                initPermissionGallery(grantResults)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initPermissionGallery(grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!flag) {
                openGallery()
            } else {
                openCamera()
            }
        } else {
            Toast.makeText(
                activity,
                resources.getString(R.string.permission_denied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initPermissionCamera(grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            flag = true
            if (!checkGalleryPermission()) {
                requestGalleryPermission()
            }
            if (checkGalleryPermission()) {
                openCamera()
            }
        } else {
            Toast.makeText(
                activity,
                resources.getString(R.string.permission_denied),
                Toast.LENGTH_SHORT
            ).show()
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
        rlFragmentRegister.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun sendDataLLoginFromRegister() {
        btnRegister.setOnClickListener {
            (activity as? SignInActivity)?.openLogin(
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
                        if (checkGalleryPermission()) {
                            openGallery()
                        } else {
                            requestGalleryPermission()
                        }
                    }
                    1 -> {
                        if (checkCameraPermission() && checkGalleryPermission()) {
                            openCamera()
                        } else {
                            requestCameraPermission()
                        }
                    }
                }
            }
            create().show()
        }
    }

    private fun initListener() {
        initChooseImage()
        sendDataLLoginFromRegister()
        initListenerHideKeyboardRegister()
        enableRegisterButton()
    }

    private fun isCorrectFormatSignUp(
    ) = edtFullName.text.toString().isFullName()
            && edtEmailSignUp.text.toString().isValidEmail() && edtNumber.text.toString()
        .isPhoneNumber() && edtPasswordSignUp.text.toString().isValidPasswordW4()
            && edtPasswordConfirm.text.toString()
        .isConfirmPassword(edtPasswordSignUp.text.toString())

    private fun enableRegisterButton() {
        edtFullName.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp()
        })
        edtEmailSignUp.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp(
            )
        })
        edtNumber.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp()
        })
        edtPasswordSignUp.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp()
        })
        edtPasswordConfirm.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormatSignUp()
        })
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_IMAGE_CAPTURE
        )
    }

    private fun requestGalleryPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_GET_CONTENT_IMAGE
        )
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openGallery() {
        val intentImage =
            Intent(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        startActivityForResult(intentImage, REQUEST_GET_CONTENT_IMAGE)
    }

    private fun checkCameraPermission() = checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun checkGalleryPermission() = checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}
