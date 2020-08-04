package com.asiantech.intern20summer1.w7.fragment

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
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.activity.MainActivity
import com.asiantech.intern20summer1.w4.fragment.CropImageFragment
import com.asiantech.intern20summer1.w4.fragment.SignUpFragment
import com.asiantech.intern20summer1.w7.MainFarmActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.`at-huybui`.fragment_register_farm.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for register fragments
 */

class RegisterFarmFragment : Fragment() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 101
        private const val PERMISSION_REQUEST_CODE = 200
        internal fun newInstance() = RegisterFarmFragment()
    }

    private var imageUri: Uri? = null
    private var isCameraAllowed = false
    private var isCheckGallery = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_farm, container, false)
    }

    override fun onResume() {
        super.onResume()
        handleCheckPermissionAfterRequest()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleForAvatarImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SignUpFragment.REQUEST_IMAGE_CAPTURE) {
                val fragment = CropImageFragment.newInstance(imageUri)
                fragment.onCropImage = this::handleReceiverUriData
                (activity as? MainActivity)?.addFragment(fragment, true)
            } else if (requestCode == SignUpFragment.REQUEST_SELECT_IMAGE_IN_ALBUM) {
                imageUri = data?.data
                val fragment = CropImageFragment.newInstance(imageUri)
                fragment.onCropImage = this::handleReceiverUriData
                (activity as? MainActivity)?.addFragment(fragment, true)
            }
        }
    }


    /**
     *
     */

    private fun handleCrop(uri: Uri?) {
        CropImage.activity(uri).setCropShape(CropImageView.CropShape.RECTANGLE).setAspectRatio(1, 1)
            .start((activity as MainFarmActivity))
    }

    /**
     * Handle function for avatar image view
     */
    private fun handleForAvatarImage() {
        imgAvatarRegister.setOnClickListener {
            val builder = (activity as MainFarmActivity).let { it1 -> AlertDialog.Builder(it1) }
            builder.setTitle("Select")
            val select = arrayOf("Camera", "Gallery")
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
     * This check permission enter camera of application
     */
    private fun isCheckCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            (activity as MainFarmActivity),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) && ((ContextCompat.checkSelfPermission(
            (activity as MainFarmActivity),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED))
    }

    /**
     * This function request permission enter to cameraE
     */
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            (activity as MainFarmActivity),
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
            (activity as MainFarmActivity).contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    /**
     * This check permission enter gallery of application
     */
    private fun isCheckGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            (activity as MainFarmActivity),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * This function request permission enter to gallery
     */
    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            (activity as MainFarmActivity), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
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