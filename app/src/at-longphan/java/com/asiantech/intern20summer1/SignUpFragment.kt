package com.asiantech.intern20summer1

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_up.*


class SignUpFragment : BaseFragment() {
    companion object {
        const val STORAGE_PERMISSION_CODE = 101
        private const val PICK_IMAGE_REQUEST = 1
        private const val OPEN_CAMERA_REQUEST = 2
        var image_uri: Uri? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListener()
    }

    private fun checkStoragePermissions() : Boolean{
        val permission = checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCameraPermissions(): Boolean {
        val permission1 = checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
        val permission2 = checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return (permission1 == PackageManager.PERMISSION_GRANTED
                && permission2 == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            OPEN_CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED && grantResults[1] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera()
                } else {
                    makeCameraRequest()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri? = data?.data
                    imgAvatar.setImageURI(uri)
                }
            }
            OPEN_CAMERA_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    imgAvatar.setImageURI(image_uri)
                }
            }
        }
    }

    private fun makeStorageRequest() {
        this.activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun makeCameraRequest() {
        this.activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                OPEN_CAMERA_REQUEST
            )
        }
    }

    private fun handleListener() {
        imgAvatar.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Choose an option:")
            // add a list
            val optionList = arrayOf("Pick an image in gallery", "Take a new photo")
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if(checkStoragePermissions()){
                            pickImage()
                        } else {
                            makeStorageRequest()
                        }
                    }
                    1 -> {
                        if (checkCameraPermissions()) {
                            openCamera()
                        } else {
                            makeCameraRequest()
                        }
                    }
                }
            }
            // create and show the alert dialog
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Pick image"),
            PICK_IMAGE_REQUEST
        )
    }

    private fun openCamera() {
        val values = ContentValues()
        image_uri =
            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(
            Intent.createChooser(intent, "Open camera"),
            OPEN_CAMERA_REQUEST
        )
    }
}
