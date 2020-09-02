package com.asiantech.intern20summer1.w10.fragment

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import kotlinx.android.synthetic.`at-huybui`.w10_dialog_fragment_post.*

class PostDialogFragment : DialogFragment() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 101
        private const val PERMISSION_REQUEST_CODE = 200
        private const val TYPE_IMAGE = "image/*"
        internal fun newInstance() = PostDialogFragment()
    }

    private var imageUri: Uri? = null
    private var isCameraAllowed = false
    private var isCheckGallery = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_dialog_fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        handleCheckPermissionAfterRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    imgContent_Post?.setImageURI(imageUri)
                }
                REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    imageUri = data?.data
                    imgContent_Post?.setImageURI(imageUri)
                }
            }
        }
    }

    private fun initView() {
        initListener()
    }


    private fun initListener() {
        btnBack_Post?.setOnClickListener {
            dialog?.dismiss()
        }

        btnPost_Post?.setOnClickListener {
            handlePostContent()
        }

        handleForAvatarImage()
    }

    private fun handlePostContent() {

    }


    private fun handleForAvatarImage() {
        imgContent_Post.setOnClickListener {
            val builder = (activity as ApiMainActivity).let { it1 -> AlertDialog.Builder(it1) }
            builder.setTitle("select")
            val select = arrayOf("camera", "gallery")
            builder.setItems(select) { _, which ->
                when (which) {
                    0 -> {
                        if (isCheckCameraPermission()) {
                            openCamera()
                        } else {
                            isCameraAllowed = true
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
            val dialog = builder.create()
            dialog.show()
        }
    }

    /**
     * This function capture a image from camera
     */
    private fun openCamera() {
        val values = ContentValues()
        imageUri =
            (activity as ApiMainActivity).contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(
            cameraIntent,
            REQUEST_IMAGE_CAPTURE
        )
    }

    /**
     * This function take a image from gallery
     */
    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type =
            TYPE_IMAGE
        startActivityForResult(
            intentGallery,
            REQUEST_SELECT_IMAGE_IN_ALBUM
        )
    }

    private fun isCheckCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            (activity as ApiMainActivity),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) && ((ContextCompat.checkSelfPermission(
            (activity as ApiMainActivity),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED))
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            (activity as ApiMainActivity),
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun isCheckGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            (activity as ApiMainActivity),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            (activity as ApiMainActivity), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun handleCheckPermissionAfterRequest() {
        when {
            isCameraAllowed -> {
                isCameraAllowed = false
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
}
