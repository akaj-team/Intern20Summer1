package com.asiantech.intern20summer1.w12.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.activity.HomeActivity
import com.asiantech.intern20summer1.w12.activity.HomeActivity.Companion.IMAGE_FOLDER_URL
import com.asiantech.intern20summer1.w12.fragment.AddPostFragment.Companion.IMAGE_NAME
import com.asiantech.intern20summer1.w12.fragment.LoginFragment.Companion.USER_KEY_LOGIN
import com.asiantech.intern20summer1.w12.model.PostContent
import com.asiantech.intern20summer1.w12.model.User
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.remoteRepository.dataResource.UpdateDataResource
import com.asiantech.intern20summer1.w12.view_model.UpdateViewModel
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_update.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UpdatePostFragment : Fragment() {

    private var user = User(0, "", "", "")
    private var flag = false
    private var imageURI: Uri? = null
    private var token: String? = null
    private var content: String? = null
    private var imageString: String? = null
    private var postId: Int = 0
    private var viewModel : UpdateDataResource? = null

    companion object {
        private const val CROP_IMAGE_HEIGHT = 1
        private const val CROP_IMAGE_WIDTH = 1
        private const val CAMERA_REQUEST_CODE = 111
        private const val GALLERY_REQUEST_CODE = 112
        private const val IMAGE_URI_QUALITY = 100
        private const val KEY_VALUE = "data"
        private const val ID_KEY = "id"
        private const val IMAGE_KEY = "image"
        private const val CONTENT_KEY = "content"
        private const val TOKEN_KEY = "token"
        internal fun newInstance(id: Int, imageName: String?, content: String?, token: String?,user: User?) =
            UpdatePostFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_KEY, id)
                    putString(IMAGE_KEY, imageName)
                    putString(CONTENT_KEY, content)
                    putString(TOKEN_KEY, token)
                    putSerializable(USER_KEY_LOGIN,user)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = UpdateViewModel(RemoteRepository())
        getDataFromActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getData()
        return inflater.inflate(R.layout.w12_fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun getData() {
        arguments?.let {
            token = it.getString(TOKEN_KEY)
            content = it.getString(CONTENT_KEY)
            imageString = it.getString(IMAGE_KEY)
            postId = it.getInt(ID_KEY)
        }
        (arguments?.getSerializable(USER_KEY_LOGIN) as? User)?.let {
            user.id = it.id
            user.email = it.email
            user.full_name = it.full_name
            user.token = it.token
        }
    }

    private fun initView() {
        edtContent?.setText(content)
        if (imageString == "") {
            imgItem.setImageResource(R.mipmap.ic_launcher_round)
        } else {
            Glide.with(this).load(IMAGE_FOLDER_URL + imageString).into(imgItem)
        }

    }

    private fun getDataFromActivity() {
        (arguments?.getSerializable(HomeFragment.USER_KEY) as? User)?.let {
            user.id = it.id
            user.email = it.email
            user.full_name = it.full_name
            user.token = it.token
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
                            getImageUri(it)?.let(this::cropImage)
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

    private fun initListener() {
        handleListenerItemImageView()
        handleUpdatePost()
        handleBackImageViewListener()
    }

    private fun handleBackImageViewListener(){
        imgBack.setOnClickListener {
            (activity as? HomeActivity)?.onBackPressed()
        }
    }

    private fun handleListenerItemImageView() {
        imgItem.setOnClickListener {
            showListAlertDialog()
        }
    }

    private fun showListAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.w12_register_fragment_set_avatar_dialog_list_title))
        val items = arrayOf(
            resources.getString(R.string.w12_register_fragment_choose_set_avatar_dialog_from_gallery),
            resources.getString(R.string.w12_register_fragment_choose_set_avatar_dialog_from_camera)
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

    private fun handleUpdatePost() {
        tvUpdate.setOnClickListener {
            updatePost()
        }
    }

    @SuppressLint("CheckResult")
    private fun updatePost() {
        viewModel?.updatePost(token,postId,getImageFile(), PostContent(edtContent.text.toString()))
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (it.isSuccessful) {
                    Log.d("TAG0000", "updatePost: update success")
                    (activity as? HomeActivity)?.replaceFragmentHome(HomeFragment.newInstance(user),false)
                } else {
                    Log.d("TAG0000", "updatePost: ${it.code()}")
                }
            }, {
                // No-ops
                Log.d("TAG0000", "updatePost: $it")
            })
    }

    private fun getImageFile(): MultipartBody.Part? {
        if (imageURI == null) {
            return imageString?.let {
                MultipartBody.Part.createFormData(IMAGE_NAME, it)
            }
        } else {
            imageURI?.let {
                val file = File(getPath(it))
                val image =
                    file.asRequestBody(AddPostFragment.IMAGE_KEY.toMediaTypeOrNull())
                return MultipartBody.Part.createFormData(IMAGE_NAME, file.name, image)
            }
        }
        return null
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
