package com.asiantech.intern20summer1.week10.fragments

import android.Manifest
import android.app.Activity
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.api.ApiClient
import com.asiantech.intern20summer1.week10.extensions.handleOnTouchScreen
import com.asiantech.intern20summer1.week10.fragments.AddNewPostFragment.Companion.KEY_IMAGE_GALLERY
import com.asiantech.intern20summer1.week10.fragments.AddNewPostFragment.Companion.OPEN_CAMERA_REQUEST
import com.asiantech.intern20summer1.week10.fragments.AddNewPostFragment.Companion.PICK_IMAGE_REQUEST
import com.asiantech.intern20summer1.week10.fragments.HomeFragment.Companion.KEY_STRING_TOKEN
import com.asiantech.intern20summer1.week10.models.Body
import com.asiantech.intern20summer1.week10.models.PostResponse
import com.asiantech.intern20summer1.week10.views.HomeApiActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-linhle`.fragment_api_update_post.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class UpdatePostFragment : Fragment() {
    companion object {
        private const val KEY_STRING_CONTENT = "content"
        private const val KEY_STRING_IMAGE = "image"
        private const val KEY_INT_ID = "id"
        fun newInstance(token: String?, content: String?, imageName: String?, id: Int) =
            UpdatePostFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_STRING_TOKEN, token)
                    putString(KEY_STRING_CONTENT, content)
                    putString(KEY_STRING_IMAGE, imageName)
                    putInt(KEY_INT_ID, id)
                }
            }
    }

    private val apiImageUrl = "https://at-a-trainning.000webhostapp.com/images/"
    private var token: String? = null
    private var content: String? = null
    private var imageName: String? = null
    private var postId: Int = 0
    private var checkCameraStatus = false
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getData()
        return inflater.inflate(R.layout.fragment_api_update_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarUpdatePost.title = getString(R.string.update_post_fragment_toolbar_title)
        handleOnClickListener()
        handleClickingAddPostImage()
        handleRenderDataInView()
        handleOnTouchScreen(llUpdatePostMain)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_CAMERA_REQUEST -> {
                    if (!isStoragePermissionsAllowed()) {
                        makeStorageRequest()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == OPEN_CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkCameraStatus = true
                if (!isStoragePermissionsAllowed()) {
                    makeStorageRequest()
                } else {
                    openCamera()
                }
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!checkCameraStatus) {
                    openStorage()
                } else {
                    openCamera()
                }
            }
        }
    }

    private fun getData() {
        arguments?.let {
            token = it.getString(KEY_STRING_TOKEN)
            content = it.getString(KEY_STRING_CONTENT)
            imageName = it.getString(KEY_STRING_IMAGE)
            postId = it.getInt(KEY_INT_ID)
        }
    }

    private fun handleRenderDataInView() {
        edtContent.setText(content)
        if (imageName == "") {
            imgUpdatePost.setBackgroundResource(R.mipmap.ic_launcher)
        } else {
            Glide.with(this).load(apiImageUrl + imageName).into(imgUpdatePost)
        }
    }

    private fun handleUpdatePostApi() {
        val body = Body(edtContent.text.toString())
        val callApi = token?.let {
            ApiClient.cretePostService()?.updatePost(it, postId, handleGetImageFile(), body)
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
                        getString(R.string.update_post_fragment_toast_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun handleOnClickListener() {
        imgArrowBack.setOnClickListener {
            (activity as HomeApiActivity).onBackPressed()
        }
        imgDone.setOnClickListener {
            handleUpdatePostApi()
        }
    }

    private fun showImage(data: Intent?) {
        imageUri = data?.data
        imgUpdatePost.setImageURI(imageUri)
    }

    private fun handleGetImageFile(): MultipartBody.Part? {
        if (imageUri == null) {
            return imageName?.let { MultipartBody.Part.createFormData("image", it) }
        } else {
            imageUri?.let {
                val file = File(getPath(it))
                val image = file.asRequestBody(KEY_IMAGE_GALLERY.toMediaTypeOrNull())
                return MultipartBody.Part.createFormData("image", file.name, image)
            }
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

    private fun isStoragePermissionsAllowed() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun isCameraPermissionAllowed(): Boolean {
        val permissionCamera =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val permissionWrite =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        return permissionCamera == PackageManager.PERMISSION_GRANTED
                && permissionWrite == PackageManager.PERMISSION_GRANTED
    }

    private fun makeStorageRequest() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PICK_IMAGE_REQUEST
        )
    }

    private fun makeCameraRequest() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            OPEN_CAMERA_REQUEST
        )
    }

    private fun openStorage() {
        val intentImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intentImage.type = KEY_IMAGE_GALLERY
        startActivityForResult(intentImage, PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST)
    }

    private fun handleClickingAddPostImage() {
        imgUpdatePost.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(R.string.add_post_fragment_choose_option_dialog)
            val optionList = arrayOf(
                resources.getString(R.string.add_post_fragment_gallery),
                resources.getString(R.string.add_post_fragment_camera)
            )
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if (isStoragePermissionsAllowed()) {
                            openStorage()
                        } else {
                            makeStorageRequest()
                        }
                    }
                    1 -> {
                        if (isCameraPermissionAllowed()) {
                            openCamera()
                        } else {
                            makeCameraRequest()
                        }
                    }
                }
            }
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }
}
