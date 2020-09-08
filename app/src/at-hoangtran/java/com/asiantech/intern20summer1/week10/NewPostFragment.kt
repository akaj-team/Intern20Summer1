package com.asiantech.intern20summer1.week10

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.HomeActivity.Companion.KEY_STRING_TOKEN
import kotlinx.android.synthetic.`at-hoangtran`.fragment_new_post.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION")
class NewPostFragment : Fragment() {
    companion object {
        private const val PICK_IMAGE_REQUEST = 2
        private const val OPEN_CAMERA_REQUEST = 1
        private const val STORAGE_REQUEST = 0
        private const val CAMERA_REQUEST = 3
        private const val KEY_IMAGE = "image/*"
        internal fun newInstance(token: String?) = NewPostFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_STRING_TOKEN, token)
            }
        }
    }

    private var flag = false
    private var imageUri: Uri? = null
    private var token: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getData()
        return inflater.inflate(R.layout.fragment_new_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleListener()
        handleBackButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                OPEN_CAMERA_REQUEST -> {
                    if (!checkStoragePermission()) {
                        requestStoragePermission()
                    } else {
                        showImage(data)
                    }
                }
                PICK_IMAGE_REQUEST -> {
                    showImage(data)
                }
            }
        }
    }

    private fun handleListener() {
        imgNewPost.setOnClickListener {
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

    private fun showImage(data: Intent?) {
        imageUri = data?.data
        imgNewPost.setImageURI(imageUri)
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

    private fun getData() {
        arguments?.let {
            token = it.getString(KEY_STRING_TOKEN)
        }
    }

    private fun getImageFile(): MultipartBody.Part? {
        imageUri?.let {
            val file = File(getPath(it))
            val image = file.asRequestBody(KEY_IMAGE.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData("image", file.name, image)
        }
        return null
    }

    private fun getPath(uri: Uri?): String {
        val result: String
        val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (cursor == null) {
            result = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(index)
            cursor.close()
        }
        return result
    }

    private fun handleBackButton() {
        btnBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        btnOk.setOnClickListener {
            handleNewPostApi()
        }
    }

    private fun handleNewPostApi() {
        val body = Body(edtNewPost.text.toString())
        val callApi = token?.let {
            ApiClient.cretePostService()?.addNewPost(it, getImageFile(), body)
        }
        callApi?.enqueue(object : retrofit2.Callback<PostResponse> {
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    }
                    activity?.onBackPressed()
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.add_new_post_fragment_toast_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
