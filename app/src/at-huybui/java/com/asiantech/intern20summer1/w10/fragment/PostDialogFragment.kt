package com.asiantech.intern20summer1.w10.fragment

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiPostService
import com.asiantech.intern20summer1.w10.api.FileInformation
import com.asiantech.intern20summer1.w10.models.PostContent
import com.asiantech.intern20summer1.w10.models.ResponsePost
import com.google.gson.Gson
import kotlinx.android.synthetic.`at-huybui`.w10_dialog_fragment_post.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File


class PostDialogFragment : DialogFragment() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 101
        private const val PERMISSION_REQUEST_CODE = 200
        private const val TYPE_IMAGE = "image/*"
        internal fun newInstance() = PostDialogFragment()
    }

    private var callApi: ApiPostService? = null
    private var imageUri: Uri? = null
    private var isCameraAllowed = false
    private var isCheckGallery = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callApi = Api.getInstance()?.create(ApiPostService::class.java)
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
                    imgContent?.setImageURI(imageUri)
                }
                REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    imageUri = data?.data
                    imgContent?.setImageURI(imageUri)
                }
            }
        }
    }

    private fun initView() {
        initListener()
    }


    private fun initListener() {
        btnBack?.setOnClickListener {
            dialog?.dismiss()
        }

        btnPost?.setOnClickListener {
            handlePostContent()
        }

        handleForAvatarImage()
    }

    private fun handlePostContent() {
        val image = File(imageUri?.path.toString()).asRequestBody("image/*".toMediaTypeOrNull())
        val text = Gson().toJson(PostContent(edtContent?.text.toString())).toString()
        val body = text.toRequestBody("text".toMediaTypeOrNull())
        val preference = requireContext().getSharedPreferences(
            SplashFragment.NAME_PREFERENCE,
            Context.MODE_PRIVATE
        )
        val token = preference.getString(SplashFragment.KEY_TOKEN, "").toString()
        d("posta", "[image] " + image.toString())
        d("posta", "[body] " + body.toString())
        d("posta", "[token] " + token)
        d("posta", "open post")
        callApi?.createPost(token, createMultiPartBody(), body)
            ?.enqueue(object : retrofit2.Callback<ResponsePost> {
                override fun onResponse(
                    call: retrofit2.Call<ResponsePost>,
                    response: Response<ResponsePost>
                ) {

                    d("posta", "[onResponse]" + response.toString())
                    if (response.body()?.message == Api.MESSAGE_CREATE_POST_SUCCESS) {
                        val text = "Đăng bài viết thành công"
                        ApiMainActivity().showToast(requireContext(), text)
                        dialog?.dismiss()
                    } else {
                        val text = "Đăng bài viết không thành công"
                        ApiMainActivity().showToast(requireContext(), text)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponsePost>, t: Throwable) {
                    d("posta", "[onFailure]$call")
                    t.printStackTrace()
                }
            })
        d("posta", "finish post")
    }

    private fun createMultiPartBody(): MultipartBody.Part? {
        imageUri?.let {
            val file = FileInformation().getFile(requireContext(),it)
            val image = file.asRequestBody("image/*".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData("image", file.name, image)
        }
        return null
    }

    private fun handleForAvatarImage() {
        imgContent.setOnClickListener {
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
