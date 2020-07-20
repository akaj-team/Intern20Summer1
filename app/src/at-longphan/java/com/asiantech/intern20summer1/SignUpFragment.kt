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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_up.*
import java.io.IOException


class SignUpFragment : BaseFragment() {
    companion object {
        const val STORAGE_PERMISSION_CODE = 101
        private val TAG = SignInActivity::javaClass.name
        private const val PICK_IMAGE_REQUEST = 1
        private const val OPEN_CAMERA_REQUEST = 2
        var image_uri: Uri? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun setupStoragePermissions() {
        val permission = checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "READ_EXTERNAL_STORAGE Permission denied")
            makeStorageRequest()
        }
    }

    private fun setupCameraPermissions() {
        val permission = checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "CAMERA Permission denied")
            makeCameraRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            OPEN_CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
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
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            context?.contentResolver,
                            uri
                        )
                        imgAvatar.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            OPEN_CAMERA_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    imgAvatar.setImageURI(image_uri)
                }
            }
        }
    }
    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }*/

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
                arrayOf(Manifest.permission.CAMERA),
                OPEN_CAMERA_REQUEST
            )
        }
    }

    private fun handleListener() {
        imgAvatar.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Choose an option:")
            // add a list
            val animals = arrayOf("Pick an image in gallery", "Take a new photo")
            dialogBuilder.setItems(animals) { _, which ->
                when (which) {
                    0 -> {
                        setupStoragePermissions()
                        pickImage()
                    }
                    1 -> {
                        setupCameraPermissions()
                        openCamera()
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
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri =
            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(
            Intent.createChooser(intent, "Open camera"),
            OPEN_CAMERA_REQUEST
        )
    }
}
