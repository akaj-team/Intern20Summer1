package com.asiantech.intern20summer1.w10.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.HomeActivity
import com.asiantech.intern20summer1.w10.api.APIClient
import com.asiantech.intern20summer1.w10.api.PostAPI
import com.asiantech.intern20summer1.w10.data.PostContent
import com.asiantech.intern20summer1.w10.data.StatusResponse
import com.asiantech.intern20summer1.w10.data.User
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-sonnguyen`.w10_fragment_add_new_feed.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddPostFragment : Fragment() {

    private var user = User(0, "", "", "")
    private var flag = false
    private var imageURI: Uri? = null

    companion object {
        private const val CROP_IMAGE_HEIGHT = 1
        private const val CROP_IMAGE_WIDTH = 1
        private const val CAMERA_REQUEST_CODE = 111
        internal const val GALLERY_REQUEST_CODE = 112
        private const val IMAGE_URI_QUALITY = 100
        private const val KEY_VALUE = "data"
        internal const val IMAGE_KEY = "image/*"
        internal const val IMAGE_NAME = "image"
        internal fun newInstance(user: User): AddPostFragment {
            val addPostFragment = AddPostFragment()
            val bundle = Bundle()
            bundle.putSerializable(HomeFragment.USER_KEY, user)
            addPostFragment.arguments = bundle
            return addPostFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_add_new_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun getDataFromActivity() {
        (arguments?.getSerializable(HomeFragment.USER_KEY) as? User)?.let {
            user.id = it.id
            user.email = it.email
            user.full_name = it.full_name
            user.token = it.token
        }
    }

    private fun initListener() {
        handleListenerItemImageView()
        handleCreatePost()
        handleBackImageViewListener()
    }

    private fun initView() {
        getDataFromActivity()
        toolbarAddPost.title = getString(R.string.w10_toolBar_add_new_post_title)
    }

    private fun handleBackImageViewListener() {
        imgBack.setOnClickListener {
            (activity as? HomeActivity)?.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    flag = true
                    if (!isGalleryAllow()) {
                        requestGalleryPermission()
                    }
                    if (isGalleryAllow()) {
                        openCamera()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.w10_permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            GALLERY_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!flag) {
                        openGallery()
                    } else {
                        openCamera()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.w10_permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        imgItem.scaleType = ImageView.ScaleType.CENTER_CROP
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    if (!isGalleryAllow()) {
                        requestGalleryPermission()
                    } else {
                        (data?.extras?.get(KEY_VALUE) as? Bitmap)?.let {
                            getImageUri(it)?.let(this@AddPostFragment::cropImage)
                            imageURI = getImageUri(it)
                        }
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    imageURI = data?.data
                    cropImage(data?.data)
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
                    imageURI = result.uri
                    imgItem.setImageURI(imageURI)
                }
            }
        }
    }

    private fun handleListenerItemImageView() {
        imgItem.setOnClickListener {
            showListAlertDialog()
        }
    }

    private fun showListAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.w10register_fragment_set_avatar_dialog_list_title))
        val items = arrayOf(
            resources.getString(R.string.w10_register_fragment_choose_set_avatar_dialog_from_gallery),
            resources.getString(R.string.w10_register_fragment_choose_set_avatar_dialog_from_camera)
        )
        builder.setItems(items) { _, which ->
            when (which) {
                0 -> {
                    if (isGalleryAllow()) {
                        openGallery()
                    } else {
                        requestGalleryPermission()
                    }
                }
                1 -> {
                    if (isCameraAllow() && isGalleryAllow()) {
                        openCamera()
                    } else {
                        requestCameraPermission()
                    }
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    private fun requestGalleryPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            GALLERY_REQUEST_CODE
        )
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val intentImage =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentImage, GALLERY_REQUEST_CODE)
    }

    private fun isCameraAllow() = checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun isGalleryAllow() = checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun handleCreatePost() {
        tvAdd.setOnClickListener {
            val file = File(getPath(imageURI))
            val fileRequestBody = file.asRequestBody(IMAGE_KEY.toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData(IMAGE_NAME, file.name, fileRequestBody)
            val service = APIClient.createServiceClient()?.create(PostAPI::class.java)
            val call =
                service?.createPost(user.token, PostContent(edtContent.text.toString()), part)
            call?.enqueue(object : Callback<StatusResponse> {
                override fun onResponse(
                    call: Call<StatusResponse>,
                    response: Response<StatusResponse>
                ) {
                }

                override fun onFailure(call: Call<StatusResponse>, t: Throwable) {}
            })
            (activity as? HomeActivity)?.replaceFragmentHome(HomeFragment.newInstance(user), false)
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, IMAGE_URI_QUALITY, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                activity?.contentResolver,
                inImage,
                resources.getString(R.string.w10_image_title),
                null
            )
        return Uri.parse(path)
    }

    private fun cropImage(uri: Uri?) {
        context?.let { context ->
            CropImage.activity(uri)
                .setAspectRatio(CROP_IMAGE_WIDTH, CROP_IMAGE_HEIGHT)
                .start(context, this)
        }
    }

    private fun getPath(uri: Uri?): String? {
        val path: String
        val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (cursor == null) {
            path = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = cursor.getString(idx)
            cursor.close()
        }
        return path
    }
}
