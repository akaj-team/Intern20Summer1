package com.asiantech.intern20summer1.week7

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
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
import android.widget.EditText
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-hoangtran`.fragment_register.*
import java.io.ByteArrayOutputStream

@Suppress("UNREACHABLE_CODE", "DEPRECATION")
class RegisterFragment : Fragment() {
    companion object {
        const val OPEN_CAMERA_REQUEST = 1
        const val PICK_IMAGE_REQUEST = 2
        const val STORAGE_REQUEST = 3
        const val CAMERA_REQUEST = 4
        const val KEY_DATA = "data"
        const val KEY_IMAGE = "image/*"
        internal fun newInstance() = RegisterFragment()
    }

    private var imageUri: String = ""
    private var flag = false
    private var ava = ""
    private var appDatabase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)

        handleEditText(edt_home_town)
        handleEditText(edt_user_name)
        handleEditText(edt_university)
        handleListener()
        handleButton()
    }

    private fun checkEditText() {
        btn_next.isEnabled =
            edt_user_name.text.isNotEmpty() && edt_university.text.isNotEmpty() && edt_home_town.text.isNotEmpty()
    }

    private fun handleEditText(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                when (edt) {
                    edt_user_name -> {
                        checkEditText()
                    }
                    edt_university -> {
                        checkEditText()
                    }
                    edt_home_town -> {
                        checkEditText()
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun handleButton() {
        btn_next?.setOnClickListener {
            val user = User()
            user.apply {
                avatar = imageUri
                userName = edt_user_name.text.toString()
                university = edt_university.text.toString()
                homeTown = edt_home_town.text.toString()
            }
            appDatabase?.getUserDAO()?.insertUser(user)
            val intent = Intent(context, GardenActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                OPEN_CAMERA_REQUEST -> {
                    if (!checkStoragePermission()) {
                        requestStoragePermission()
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

    private fun handleListener() {
        img_avatar.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Choose an option:")
            val optionList = arrayOf("Gallery", "Camera")
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if (checkStoragePermission()) {
                            openGallery()
                        } else {
                            requestStoragePermission()
                        }
                    }
                    1 -> {
                        if (checkCameraPermission()) {
                            openCamera()
                        } else {
                            requestCameraPermission()
                        }
                    }
                }
            }
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            cameraIntent,
            OPEN_CAMERA_REQUEST
        )
    }

    private fun openGallery() {
        val intentImage =
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        intentImage.type =
            KEY_IMAGE
        startActivityForResult(
            intentImage,
            PICK_IMAGE_REQUEST
        )
    }

    private fun cropImageCamera(data: Intent?) {
        (data?.extras?.get(KEY_DATA) as? Bitmap)?.let {
            getImageUri(it)?.let { uri -> handleCropImage(uri) }
        }
    }

    private fun cropImageGallery(data: Intent?) {
        data?.data.let {
            it?.let { it1 -> handleCropImage(it1) }
        }
    }

    private fun showImage(data: Intent?) {
        if (data != null) {
            CropImage.getActivityResult(data).uri.apply {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, this)
                ava = this.toString()
                img_avatar.setImageBitmap(bitmap)
            }
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
            MediaStore.Images.Media.insertImage(
                context?.contentResolver,
                inImage,
                "Title",
                null
            )
        return Uri.parse(path)
    }

    private fun checkStoragePermission(): Boolean =
        checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun checkCameraPermission(): Boolean =
        checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestStoragePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            STORAGE_REQUEST
        )
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                flag = true
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    openCamera()
                }
            }
        }
        if (requestCode == STORAGE_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!flag) {
                    openGallery()
                } else {
                    openCamera()
                }
            }
        }
    }

}
