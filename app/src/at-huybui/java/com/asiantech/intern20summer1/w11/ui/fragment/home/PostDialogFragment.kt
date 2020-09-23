package com.asiantech.intern20summer1.w11.ui.fragment.home

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.data.source.remote.network.ApiClient
import com.asiantech.intern20summer1.w11.data.models.PostContent
import com.asiantech.intern20summer1.w11.ui.activity.ApiMainActivity
import com.asiantech.intern20summer1.w11.ui.viewmodel.ViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-huybui`.w10_dialog_fragment_post.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 02/09/2020.
 * This is PostDialogFragment class. It is fragment to display add new post page
 */

class PostDialogFragment : DialogFragment() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 101
        private const val PERMISSION_REQUEST_CAMERA_CODE = 200
        private const val PERMISSION_REQUEST_GALLERY_CODE = 201
        private const val TYPE_IMAGE = "image/*"
        private const val TYPE_TEXT = "text"
        internal fun newInstance() = PostDialogFragment()
    }

    internal var onPostClick: () -> Unit = {}
    private var imageUri: Uri? = null
    private var isCameraAllowed = false
    private var isCheckGallery = false
    private var viewModel: ViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        return inflater.inflate(R.layout.w10_dialog_fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CAMERA_CODE -> {
                if (isCheckCameraPermission()) {
                    openCamera()
                }
            }
            PERMISSION_REQUEST_GALLERY_CODE -> {
                if (isCheckGalleryPermission()) {
                    openGallery()
                }
            }
        }
    }

    private fun initView() {
        initDialog()
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
        progressBar?.visibility = View.VISIBLE
        val postJson = Gson().toJson(PostContent(edtContent?.text.toString())).toString()
        val body = postJson.toRequestBody(TYPE_TEXT.toMediaTypeOrNull())
        val token = viewModel?.getToken()
        viewModel
            ?.createPost(token.toString(), imageUri.toString(), body)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { response ->
                if (response.body()?.message == ApiClient.MESSAGE_CREATE_POST_SUCCESS) {
                    val text = getString(R.string.w10_complete_post)
                    ApiMainActivity().showToast(requireContext(), text)
                    onPostClick.invoke()
                    dialog?.dismiss()
                } else {
                    val text = getString(R.string.w10_error_post)
                    ApiMainActivity().showToast(requireContext(), text)
                }
                progressBar?.visibility = View.INVISIBLE
            }
    }

    private fun handleForAvatarImage() {
        imgContent.setOnClickListener {
            val builder = (activity as ApiMainActivity).let { it1 -> AlertDialog.Builder(it1) }
            builder.setTitle(getString(R.string.w10_select_picture))
            val select = arrayOf(getString(R.string.w10_camera), getString(R.string.w10_gallery))
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
        imageUri = (activity as ApiMainActivity).contentResolver.insert(
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
        intentGallery.type = TYPE_IMAGE
        startActivityForResult(intentGallery, REQUEST_SELECT_IMAGE_IN_ALBUM)
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
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CAMERA_CODE
        )
    }

    private fun isCheckGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            (activity as ApiMainActivity),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestGalleryPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_GALLERY_CODE
        )
    }

    private fun initDialog() {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    private fun setupViewModel() {
//        viewModel = ViewModel(RemoteRepository(requireContext()), LocalRepository(requireContext()))
    }
}
