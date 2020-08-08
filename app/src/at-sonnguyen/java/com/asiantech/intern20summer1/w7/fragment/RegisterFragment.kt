package com.asiantech.intern20summer1.w7.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.activity.HomeActivity
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_register.*
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class RegisterFragment : Fragment() {
    private var isUsername: Boolean = false
    private var isUniversity: Boolean = false
    private var isHomeTown: Boolean = false
    private var flag: Boolean = false
    private var avatarUri = ""

    companion object {
        internal const val KEY_VALUE = "data"
        private const val CROP_IMAGE_HEIGHT = 1
        private const val CROP_IMAGE_WIDTH = 1
        private const val IMAGE_URI_QUALITY = 100
        private const val CAMERA_REQUEST_CODE = 111
        private const val GALLERY_REQUEST_CODE = 112
        fun newInstance() = RegisterFragment()
        internal const val SHARED_PREFERENCE_FILE = "userSharedPreference"
        internal const val SHARED_PREFERENCE_USER_NAME_KEY = "userName"
        internal const val SHARED_PREFERENCE_UNIVERSITY_KEY = "university"
        internal const val SHARED_PREFERENCE_HOME_TOWN_KEY = "homeTown"
        internal const val SHARED_PREFERENCE_AVATAR_KEY = "avatarUser"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun handleUsernameEditTextListener() {
        edtUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                isUsername = edtUsername.text.isNotEmpty()
                setEnableButton()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun handleHomeTownEditTextListener() {
        edtHomeTown.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                isHomeTown = edtHomeTown.text.isNotEmpty()
                setEnableButton()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun handleUniversityEditText() {
        edtUniversity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                isUniversity = edtUniversity.text.isNotEmpty()
                setEnableButton()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun setEnableButton() {
        btnNext.isEnabled = isUsername && isUniversity && isHomeTown
    }

    private fun initListener() {
        handleHomeTownEditTextListener()
        handleUniversityEditText()
        handleUsernameEditTextListener()
        setEnableButton()
        handleListenerAvatarImageView()
        handleRegisterButtonListener()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    flag = true
                    if (!isGalleryAllow()) {
                        requestGalleryPermission()
                    }
                    if (isGalleryAllow()) {
                        openCamera()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.w7_permission_denied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            GALLERY_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!flag) {
                        openGallery()
                    } else {
                        openCamera()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.w7_permission_denied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        imgAvatarRegister.scaleType = ImageView.ScaleType.CENTER_CROP
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    if (!isGalleryAllow()) {
                        requestGalleryPermission()
                    } else {
                        (data?.extras?.get(KEY_VALUE) as? Bitmap)?.let {
                            getImageUri(it)?.let(this@RegisterFragment::cropImage)
                        }
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    cropImage(data?.data)
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
                    val resultUri: Uri = result.uri
                    avatarUri = resultUri.toString()
                    Toast.makeText(activity?.applicationContext,avatarUri,Toast.LENGTH_SHORT).show()
                    imgAvatarRegister.setImageURI(resultUri)
                }
            }
        }
    }

    private fun showListAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Set avatar")
        val items = arrayOf(
            "Gallery",
            "Camera"
        )
        builder.setItems(items) { _, which ->
            when (which) {
                0 -> {
                    if (isGalleryAllow()) {
                        openGallery()
                    } else {
                        requestGalleryPermission()
                    }
                }
                1 -> {
                    if (isCameraAllow() && isGalleryAllow()) {
                        openCamera()
                    } else {
                        requestCameraPermission()
                    }
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    private fun requestGalleryPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            GALLERY_REQUEST_CODE
        )
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val intentImage =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentImage, GALLERY_REQUEST_CODE)
    }

    private fun isCameraAllow() = checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun isGalleryAllow() = checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, IMAGE_URI_QUALITY, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                activity?.contentResolver,
                inImage,
                resources.getString(R.string.w7_image_title),
                null
            )
        return Uri.parse(path)
    }

    private fun cropImage(uri: Uri?) {
        context?.let { context ->
            CropImage.activity(uri)
                .setAspectRatio(CROP_IMAGE_WIDTH, CROP_IMAGE_HEIGHT)
                .start(context, this)
        }
    }

    private fun handleListenerAvatarImageView() {
        imgAvatarRegister.setOnClickListener {
            showListAlertDialog()
        }
    }

    private fun handleRegisterButtonListener() {
        val sharedPreferences = activity?.getSharedPreferences(
            SHARED_PREFERENCE_FILE,
            Context.MODE_PRIVATE
        )
        btnNext.setOnClickListener {
            val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
            editor?.apply {
                putString(SHARED_PREFERENCE_USER_NAME_KEY, edtUsername.text.toString())
                putString(SHARED_PREFERENCE_UNIVERSITY_KEY, edtUniversity.text.toString())
                putString(SHARED_PREFERENCE_HOME_TOWN_KEY, edtHomeTown.text.toString())
                putString(SHARED_PREFERENCE_AVATAR_KEY, avatarUri)
                apply()
            }
            val intent = Intent(activity, HomeActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }
}
