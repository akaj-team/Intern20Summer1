package com.asiantech.intern20summer1.week10

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.HomeFragment.Companion.KEY_TOKEN
import com.asiantech.intern20summer1.week10.NewPostFragment.Companion.KEY_IMAGE
import kotlinx.android.synthetic.`at-hoangtran`.fragment_new_post.*
import kotlinx.android.synthetic.`at-hoangtran`.fragment_update.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION")
class UpdateFragment : Fragment() {
    companion object {

        private const val KEY_CONTENT = "content"
        private const val KEY_IMAGE_NAME = "imageName"
        private const val KEY_ID = "id"
        internal fun newInstance(token: String?, content: String?, imageName: String?, id: Int) =
            UpdateFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TOKEN, token)
                    putString(KEY_CONTENT, content)
                    putString(KEY_IMAGE_NAME, imageName)
                    putInt(KEY_ID, id)
                }
            }
    }

    private var apiImageUrl = "https://at-a-training.000webhostapp.com/images/"
    private var token: String? = null
    private var content: String? = null
    private var imageName: String? = null
    private var postId: Int = 0
    private var flag = false
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getData()
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleListener()
        handleUpdateApi()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                NewPostFragment.OPEN_CAMERA_REQUEST -> {
                    if (!checkStoragePermission()) {
                        requestStoragePermission()
                    } else {
                        showImage(data)
                    }
                }
                NewPostFragment.PICK_IMAGE_REQUEST -> {
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
            NewPostFragment.OPEN_CAMERA_REQUEST
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
            NewPostFragment.PICK_IMAGE_REQUEST
        )
    }

    private fun showImage(data: Intent?) {
        imageUri = data?.data
        imgNewPost.setImageURI(imageUri)
    }

    private fun checkStoragePermission(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun checkCameraPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestStoragePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            NewPostFragment.STORAGE_REQUEST
        )
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA), NewPostFragment.CAMERA_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NewPostFragment.CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                flag = true
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    openCamera()
                }
            }
        }
        if (requestCode == NewPostFragment.STORAGE_REQUEST) {
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
            token = it.getString(KEY_TOKEN)
            content = it.getString(KEY_CONTENT)
            imageName = it.getString(KEY_IMAGE_NAME)
            postId = it.getInt(KEY_ID)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPath(uri: Uri?): String {
        val result: String
        val cursor = uri?.let {
            context?.contentResolver?.query(it, null, null, null)
        }
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getImageFile(): MultipartBody.Part? {
        if (imageUri == null) {
            return imageName?.let {
                MultipartBody.Part.createFormData("image", it)
            }
        } else {
            imageUri?.let {
                val file = File(getPath(it))
                val image = file.asRequestBody(KEY_IMAGE.toMediaTypeOrNull())
                return MultipartBody.Part.createFormData("image", file.name, image)
            }
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleUpdateApi() {
        val body = Body(edtUpdate.text.toString())
        val callApi = token?.let {
            ApiClient.createPostService()?.updatePost(it, postId, getImageFile(), body)
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
                    Toast.makeText(activity, "update post fail", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
