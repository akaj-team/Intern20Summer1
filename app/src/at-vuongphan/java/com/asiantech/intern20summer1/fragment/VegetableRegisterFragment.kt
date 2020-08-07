package com.asiantech.intern20summer1.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.VegetableFarmMainActivity
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.textChangedListener
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-vuongphan`.w7_register_fragment.*
import java.io.ByteArrayOutputStream

class VegetableRegisterFragment : Fragment() {
    private var flag = false
    private var imageUri: String? = ""

    companion object {
        internal fun newInstance(): VegetableRegisterFragment {
            return VegetableRegisterFragment()
        }

        private const val REQUEST_IMAGE_CAPTURE = 111
        private const val REQUEST_GET_CONTENT_IMAGE = 222
        internal const val SHARED_FILE = "userSharedPreference"
        internal const val SHARED_USER_NAME_KEY = "userName"
        internal const val SHARED_UNIVERSITY_KEY = "university"
        internal const val SHARED_AVATAR_KEY = "avatar"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
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

    private fun initListener() {
        initEnableButton()
        initListenerHideKeyboardLogin()
        initDialogImage()
        initButtonNext()
    }

    private fun initButtonNext() {
        val sharedPreferences = activity?.getSharedPreferences(
            SHARED_FILE,
            Context.MODE_PRIVATE
        )
        btnNext.setOnClickListener {
            val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
            editor?.apply {
                putString(SHARED_USER_NAME_KEY, edtUserName.text.toString())
                putString(SHARED_UNIVERSITY_KEY, edtUniversity.text.toString())
                putString(SHARED_AVATAR_KEY, imageUri)
                apply()
            }
            val intent = Intent(activity, VegetableFarmMainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        rlRegister.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun cropImageCamera(data: Intent?) {
        (data?.extras?.get("data") as? Bitmap)?.let {
            getImageUri(it)?.let { uri -> handleCropImage(uri) }
        }
    }

    private fun cropImageGallery(data: Intent?) {
        data?.data.let {
            it?.let { it1 -> handleCropImage(it1) }
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
        @Suppress("DEPRECATION") val path =
            MediaStore.Images.Media.insertImage(context?.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun showImage(data: Intent?) {
        CropImage.getActivityResult(data).uri.apply {
            if (this != null) {
                @Suppress("DEPRECATION") val bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, this)
                imageUri = this.toString()
                imgAvatar.setImageBitmap(bitmap)
            }
        }
    }

    private fun initDialogImage() {
        imgAvatar.setOnClickListener {
            activity?.let { it1 ->
                AlertDialog.Builder(it1).apply {
                    setTitle("Choose Action")
                    setMessage("Take photo from camera or gallery?")
                    setPositiveButton("Camera") { _, _ ->
                        if (checkCameraPermission() && checkGalleryPermission()) {
                            openCamera()
                        } else {
                            requestCameraPermission()
                        }
                    }
                    setNegativeButton("Gallery") { _, _ ->
                        if (checkGalleryPermission()) {
                            openGallery()
                        } else {
                            requestGalleryPermission()
                        }
                    }
                    setNeutralButton("Cancel") { _, _ ->
                    }
                }.create().show()
            }
        }
    }

    private fun isCorrectFormat() =
        edtUserName.text.toString().isNotEmpty() && edtUniversity.text.toString().isNotEmpty() &&
                edtHome.text.toString().isNotEmpty()

    private fun initEnableButton() {
        initTextChangeListener(edtHome)
        initTextChangeListener(edtUserName)
        initTextChangeListener(edtUniversity)
    }

    private fun initTextChangeListener(editText: EditText) {
        editText.textChangedListener(onTextChanged = { _, _, _, _ ->
            btnNext.apply {
                isEnabled = isCorrectFormat()
            }
            setBackgroundColor()
        })
    }

    private fun setBackgroundColor() {
        if (btnNext.isEnabled) {
            btnNext.setBackgroundResource(R.drawable.bg_register_button_radius_enable)
        } else {
            btnNext.setBackgroundResource(R.drawable.bg_register_button_radius_dis_enable)
        }
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
                "permission_denied",
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
                "permission_denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openCamera() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE)
    }

    private fun openGallery() {
        startActivityForResult(
            (Intent(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            )), REQUEST_GET_CONTENT_IMAGE
        )
    }

    private fun checkCameraPermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun checkGalleryPermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

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
}
