package com.asiantech.intern20summer1.week7.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.other.RequestCode
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.entity.User
import com.asiantech.intern20summer1.week7.other.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.`at-longphan`.activity_register_w7.*

class RegisterWeekSevenActivity : AppCompatActivity() {

    private var database: PlantRoomDatabase? = null
    private var imageCaptureUri: Uri? = null
    private var userInserted: User? = null

    companion object {

        private const val KEY_IMAGE = "data"
        private const val TITLE_CAMERA = "camera"
        private const val TITLE_GALLERY = "gallery"
        private const val ASPECT_RATIO_X = 1
        private const val ASPECT_RATIO_Y = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_w7)
        configStatusBarColor()

        database = PlantRoomDatabase.getDatabase(this)

        handleListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCode.PICK_IMAGE_REQUEST ->
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { startCropImage(it) }
                }
            RequestCode.OPEN_CAMERA_REQUEST ->
                if (resultCode == Activity.RESULT_OK) {
                    cropImageCamera(data)
                }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                showImage(data)
            }
        }
    }

    private fun cropImageCamera(data: Intent?) {
        (data?.extras?.get(KEY_IMAGE) as? Bitmap)?.let {
            getImageUri(it)?.let { uri -> startCropImage(uri) }
        }
    }

    private fun showImage(data: Intent?) {
        CropImage.getActivityResult(data).uri?.apply {
            imgAvatarWeek7.setImageURI(this)
            imageCaptureUri = this
        }
    }

    private fun startCropImage(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y)
            .start(this)
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val path =
            MediaStore.Images.Media.insertImage(
                contentResolver,
                inImage,
                getString(R.string.image_path_title),
                null
            )
        return Uri.parse(path)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RequestCode.OPEN_CAMERA_REQUEST -> {
                if (checkCameraPermissions()) {
                    openCamera()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.camera_permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            RequestCode.PICK_IMAGE_REQUEST -> {
                if (checkStoragePermissions()) {
                    pickImage()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.storage_permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun configStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun handleListeners() {
        handleEditTextUsernameListener()
        handleEditTextUniversityListener()
        handleEditTextHomeTownListener()
        handleImageViewAvatarListener()
        handleButtonNextRegisterWeek7Clicked()
    }

    private fun handleEditTextUsernameListener() {
        edtUsernameWeek7?.addTextChangedListener {
            handleButtonNextRegisterWeek7Listener()
        }
    }

    private fun handleEditTextUniversityListener() {
        edtUniversityWeek7?.addTextChangedListener {
            handleButtonNextRegisterWeek7Listener()
        }
    }

    private fun handleEditTextHomeTownListener() {
        edtHomeTownWeek7?.addTextChangedListener {
            handleButtonNextRegisterWeek7Listener()
        }
    }

    @SuppressLint("ResourceType")
    private fun handleButtonNextRegisterWeek7Listener() {
        btnNextRegisterWeek7?.let {
            if (edtUsernameWeek7.text.toString().isNotEmpty()
                && edtUniversityWeek7.text.toString().isNotEmpty()
                && edtHomeTownWeek7.text.toString().isNotEmpty()
            ) {
                it.setBackgroundResource(R.drawable.bg_button_next_register_enable_w7)
                it.setTextColor(Color.parseColor(resources.getString(R.color.colorButtonNextRegisterEnableWeek7Text)))
                it.isEnabled = true
            } else {
                it.setBackgroundResource(R.drawable.bg_button_next_register_disable_w7)
                it.setTextColor(Color.parseColor(resources.getString(R.color.colorButtonNextRegisterDisableWeek7Text)))
                it.isEnabled = false
            }
        }
    }

    private fun handleButtonNextRegisterWeek7Clicked() {
        btnNextRegisterWeek7?.setOnClickListener {
            database?.userDao()?.insert(
                User(
                    0,
                    edtUsernameWeek7.text.toString(),
                    edtUniversityWeek7.text.toString(),
                    edtHomeTownWeek7.text.toString(),
                    imageCaptureUri?.toString()
                )
            )

            userInserted = database?.userDao()?.getLastInsert()

            saveRegisterUserData()

            startActivity(Intent(baseContext, HomeWeekSevenActivity::class.java))
            finish()
        }
    }

    private fun saveRegisterUserData() {
        val sharePref: SharedPreferences =
            getSharedPreferences(USER_DATA_PREFS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharePref.edit()
        userInserted?.userId?.let { editor.putInt(ID_KEY, it) }
        editor.putString(USERNAME_KEY, userInserted?.userName)
        editor.putString(UNIVERSITY_KEY, userInserted?.university)
        editor.putString(HOMETOWN_KEY, userInserted?.homeTown)
        editor.putString(AVATAR_URL_KEY, userInserted?.avatar)
        editor.apply()
    }

    private fun handleImageViewAvatarListener() {
        imgAvatarWeek7?.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle(getString(R.string.title_pick_avatar_dialog))
            val optionList = arrayOf(
                getString(R.string.pick_in_gallery_option),
                getString(R.string.open_camera_option)
            )
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if (checkStoragePermissions()) {
                            pickImage()
                        } else {
                            requestStoragePermissions()
                        }
                    }
                    1 -> {
                        if (checkCameraPermissions()) {
                            openCamera()
                        } else {
                            requestCameraPermissions()
                        }
                    }
                }
            }
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    private fun checkStoragePermissions() =
        PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    private fun checkCameraPermissions(): Boolean {
        val permissionCamera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        val permissionWrite = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissionCamera == PackageManager.PERMISSION_GRANTED
                && permissionWrite == PackageManager.PERMISSION_GRANTED
    }

    private fun pickImage() {
        startActivityForResult(
            CropImage.getPickImageChooserIntent(
                this,
                TITLE_GALLERY,
                true,
                false
            ), RequestCode.PICK_IMAGE_REQUEST
        )
    }

    private fun openCamera() {
        imageCaptureUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
        val chooserIntent =
            Intent.createChooser(Intent(MediaStore.ACTION_IMAGE_CAPTURE), TITLE_CAMERA)
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri)
        startActivityForResult(chooserIntent, RequestCode.OPEN_CAMERA_REQUEST)
    }

    private fun requestStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RequestCode.PICK_IMAGE_REQUEST
            )
        }
    }

    private fun requestCameraPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), RequestCode.OPEN_CAMERA_REQUEST
            )
        }
    }
}
