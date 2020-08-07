package com.asiantech.intern20summer1.w7.launcher

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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.model.UserModel
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
        internal const val KEY_PUT = "key_put"
        internal fun newInstance() = RegisterFarmFragment()
    }

    private var imageUri: Uri? = null
    private var isCameraAllowed = false
    private var isCheckGallery = false
    private var dataBase: ConnectDataBase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_register_farm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBase = ConnectDataBase.getInMemoryDatabase(requireContext())
        handleForListener()
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
                    handleCrop(imageUri)
                }
                REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    imageUri = data?.data
                    handleCrop(imageUri)
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    imageUri = result.uri
                    imgAvatarRegister?.setImageURI(imageUri)
                }
            }
        }
    }

    private fun handleForListener() {
        handleForButton()
        handleForAvatarImage()
        handleForEditText()
    }

    private fun handleForButton() {
        btnRegisterNext?.isEnabled = false
        btnRegisterNext.setOnClickListener {
            val intent = Intent(context, MainFarmActivity::class.java)
            val name = edtUserName?.text.toString()
            val university = edtUniversity?.text.toString()
            val homeTown = edtHomeTown?.text.toString()
            val imgUri = imageUri?.toString()
            val user = UserModel(
                userName = name,
                university = university,
                homeTown = homeTown,
                imgUri = imgUri
            )

            dataBase?.accountDao()?.insertUser(user)

            val userModel = dataBase?.accountDao()?.getUser()
            var st = "${userModel?.userName} \n ${userModel?.homeTown}\n ${userModel?.userId}"

            Toast.makeText(context, st, Toast.LENGTH_SHORT).show()

//            intent.putExtra(KEY_PUT, user as Serializable)
//            startActivity(intent)
//            (activity as LauncherFarmActivity).finish()
        }
    }

    private fun handleForEditText() {
        edtUserName?.addTextChangedListener {
            checkEditText()
        }
        edtUniversity?.addTextChangedListener {
            checkEditText()
        }
        edtHomeTown?.addTextChangedListener {
            checkEditText()
        }
    }

    private fun checkEditText() {
        if (edtUserName?.text.isNullOrEmpty() || edtUniversity?.text.isNullOrEmpty() || edtHomeTown?.text.isNullOrEmpty()) {
            btnRegisterNext.setBackgroundResource(R.drawable.bg_w7_button_register_disable)
            btnRegisterNext.isEnabled = false
        } else {
            btnRegisterNext.setBackgroundResource(R.drawable.bg_w7_select_register_button)
            btnRegisterNext.isEnabled = true
        }
    }

    private fun handleForAvatarImage() {
        imgAvatarRegister.setOnClickListener {
            val builder = (activity as LauncherFarmActivity).let { it1 -> AlertDialog.Builder(it1) }
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
     * This function will start activity to crop image
     */

    private fun handleCrop(uri: Uri?) {
        context?.let {
            CropImage.activity(uri).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1, 1)
                .start(it, this@RegisterFarmFragment)
        }
    }

    /**
     * This function capture a image from camera
     */
    private fun openCamera() {
        val values = ContentValues()
        imageUri =
            (activity as LauncherFarmActivity).contentResolver.insert(
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
        intentGallery.type = "image/*"
        startActivityForResult(
            intentGallery,
            REQUEST_SELECT_IMAGE_IN_ALBUM
        )
    }

    private fun isCheckCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            (activity as LauncherFarmActivity),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) && ((ContextCompat.checkSelfPermission(
            (activity as LauncherFarmActivity),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED))
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            (activity as LauncherFarmActivity),
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun isCheckGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            (activity as LauncherFarmActivity),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            (activity as LauncherFarmActivity), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
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
