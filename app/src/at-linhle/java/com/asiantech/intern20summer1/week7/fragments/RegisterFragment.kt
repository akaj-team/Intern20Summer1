package com.asiantech.intern20summer1.week7.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.extensions.hideSoftKeyboard
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.models.User
import com.asiantech.intern20summer1.week7.views.HomeActivity
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-linhle`.fragment_register.*
import java.io.ByteArrayOutputStream

class RegisterFragment : Fragment() {

    companion object {
        private const val KEY_IMAGE = "data"
        private const val OPEN_CAMERA_REQUEST = 1
        private const val PICK_IMAGE_REQUEST = 2
        private const val KEY_IMAGE_GALLERY = "image/*"
        private const val QUALITY_IMAGE_INDEX = 100
        private const val ASPECT_IMAGE_RATIO = 1
        internal fun newInstance() = RegisterFragment()
    }

    private var imageUri: String? = ""
    private var checkCameraStatus = false
    private var appDatabase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getInstance(requireContext())
        handleRegisterUsernameTextChanged()
        handleRegisterUniversityTextChanged()
        handleRegisterHomeTownTextChanged()
        handleNextButtonListener()
        handleProfileImage()
        handleOnTouchScreen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_CAMERA_REQUEST -> {
                    if (!isStoragePermissionsAllowed()) {
                        makeStorageRequest()
                    } else {
                        cropImageCamera(data)
                    }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == OPEN_CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkCameraStatus = true
                if (!isStoragePermissionsAllowed()) {
                    makeStorageRequest()
                } else {
                    openCamera()
                }
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!checkCameraStatus) {
                    openStorage()
                } else {
                    openCamera()
                }
            }
        }
    }

    private fun isCorrectFormat(userName: String, university: String, homeTown: String) =
        userName != "" && university != "" && homeTown != ""

    @SuppressLint("ClickableViewAccessibility")
    private fun handleOnTouchScreen() {
        llRegisterMain?.setOnTouchListener { it, _ ->
            it.clearFocus()
            it.hideSoftKeyboard()
            true
        }
    }

    private fun handleRegisterUsernameTextChanged() {
        edtUserName?.addTextChangedListener(afterTextChanged = { p0: CharSequence? ->
            btnNext?.isEnabled = isCorrectFormat(
                p0.toString(),
                edtUniversity?.text.toString(),
                edtHomeTown?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterUniversityTextChanged() {
        edtUniversity?.addTextChangedListener(afterTextChanged = { p0: CharSequence? ->
            btnNext?.isEnabled = isCorrectFormat(
                edtUserName?.text.toString(),
                p0.toString(),
                edtHomeTown?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterHomeTownTextChanged() {
        edtHomeTown?.addTextChangedListener(afterTextChanged = { p0: CharSequence? ->
            btnNext?.isEnabled = isCorrectFormat(
                edtUserName?.text.toString(),
                edtUniversity?.text.toString(),
                p0.toString()
            )
            setBackgroundButton()
        })
    }

    private fun cropImageCamera(data: Intent?) {
        (data?.extras?.get(KEY_IMAGE) as? Bitmap)?.let {
            getImageUri(it)?.let { uri -> handleCropImage(uri) }
        }
    }

    private fun cropImageGallery(data: Intent?) {
        data?.data?.let {
            handleCropImage(it)
        }
    }

    private fun showImage(data: Intent?) {
        CropImage.getActivityResult(data).uri?.apply {
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, this)
            imageUri = this.toString()
            imgSignUpUser.setImageBitmap(bitmap)
        }
    }

    private fun handleCropImage(uri: Uri) {
        context?.let {
            CropImage.activity(uri).setAspectRatio(ASPECT_IMAGE_RATIO, ASPECT_IMAGE_RATIO)
                .start(it, this)
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, QUALITY_IMAGE_INDEX, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                context?.contentResolver,
                inImage,
                resources.getString(R.string.sign_up_fragment_image_profile_title),
                null
            )
        return Uri.parse(path)
    }

    private fun isStoragePermissionsAllowed() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun isCameraPermissionAllowed(): Boolean {
        val permissionCamera =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val permissionWrite =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        return permissionCamera == PackageManager.PERMISSION_GRANTED
                && permissionWrite == PackageManager.PERMISSION_GRANTED
    }

    private fun makeStorageRequest() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PICK_IMAGE_REQUEST
        )
    }

    private fun makeCameraRequest() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), OPEN_CAMERA_REQUEST)
    }

    private fun openStorage() {
        val intentImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intentImage.type = KEY_IMAGE_GALLERY
        startActivityForResult(intentImage, PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST)
    }

    private fun handleProfileImage() {
        imgSignUpUser.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(R.string.sign_up_fragment_choose_option_dialog)
            val optionList = arrayOf(
                resources.getString(R.string.sign_up_fragment_gallery),
                resources.getString(R.string.sign_up_fragment_camera)
            )
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if (isStoragePermissionsAllowed()) {
                            openStorage()
                        } else {
                            makeStorageRequest()
                        }
                    }
                    1 -> {
                        if (isCameraPermissionAllowed()) {
                            openCamera()
                        } else {
                            makeCameraRequest()
                        }
                    }
                }
            }
            // Create and show the alert dialog
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    private fun setBackgroundButton() {
        if (btnNext.isEnabled) {
            btnNext?.setBackgroundResource(R.drawable.bg_next_button_enabled)
        } else {
            btnNext?.setBackgroundResource(R.drawable.bg_next_button_disabled)
        }
    }

    private fun handleNextButtonListener() {
        btnNext?.setOnClickListener {
            val imgUri = imageUri
            val user = User()
            user.apply {
                avatar = imgUri
                userName = edtUserName?.text.toString()
                university = edtUniversity?.text.toString()
                homeTown = edtHomeTown?.text.toString()
            }
            appDatabase?.getUserDao()?.insertUser(user)
            val intent = Intent(context, HomeActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }
}
