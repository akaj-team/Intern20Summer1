package com.asiantech.intern20summer1.week10.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.api.RetrofitClient
import com.asiantech.intern20summer1.week10.model.CreatePostBody
import com.asiantech.intern20summer1.week10.model.StatusResponse
import com.asiantech.intern20summer1.week10.other.IMAGE_FOLDER_URL
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-longphan`.fragment_new_post_w10.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import javax.net.ssl.HttpsURLConnection

class NewPostFragment : Fragment() {

    companion object {
        internal fun newInstance(
            token: String?,
            idEditPost: Int? = null,
            image: String? = null,
            content: String? = null
        ) = NewPostFragment().apply {
            arguments = Bundle().apply {
                putString(TOKEN_KEY, token)
                if (idEditPost != null) {
                    putInt(ID_EDIT_POST_KEY, idEditPost)
                    putString(IMAGE_KEY, image)
                    putString(CONTENT_KEY, content)
                }

            }
        }

        internal const val IMAGE_TYPE_KEY = "image/*"
        private const val TOKEN_KEY = "token_key"
        private const val ID_EDIT_POST_KEY = "id_edit_post_key"
        private const val IMAGE_KEY = "image_key"
        private const val CONTENT_KEY = "content_key"
        private const val DEFAULT_NAME_IMAGE_UPLOAD = "image"
    }

    private var token: String? = null
    private var idEditPost: Int? = null
    private var imageUri: Uri? = null
    private var imageName: String? = null
    private var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments?.getString(TOKEN_KEY)
        if (arguments?.getInt(ID_EDIT_POST_KEY, -1) != -1) {
            idEditPost = arguments?.getInt(ID_EDIT_POST_KEY)
            imageName = arguments?.getString(IMAGE_KEY)
            content = arguments?.getString(CONTENT_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_post_w10, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        handleListeners()
    }

    private fun initViews() {
        /**
         * idEditPost != -1
         *      -> edit post
         */
        if (idEditPost != -1) {
            //button
            btnPostW10?.text = getString(R.string.button_done_edit_w10)
            //title
            tvTitleFragmentNewPostW10?.text =
                getString(R.string.text_view_edit_post_toolbar_title_w10)
            //image
            //if (imageUri.toString().isNotBlank()) {
            if (imageName != "") {
                context?.let {
                    Glide.with(it).load(IMAGE_FOLDER_URL + imageName).into(imgUploadImagePostW10)
                }
            }
            //content
            edtContentPostW10?.setText(content)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleListeners() {

        imgBackNewPost?.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        imgUploadImagePostW10?.setOnClickListener {
            onChoseImageUploadFile()
        }

        edtContentPostW10?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (edtContentPostW10.text.toString().trim().isEmpty()) {
                    edtContentPostW10.error = getString(R.string.error_required_field_text)
                }
            }
        }

        edtContentPostW10?.addTextChangedListener {
            if (edtContentPostW10.text.toString().trim().isEmpty()) {
                toggleButtonPostAvailable(false)
            } else {
                edtContentPostW10.error = null
                toggleButtonPostAvailable(true)
            }
        }

        btnPostW10?.setOnClickListener {
            if (idEditPost == null) {
                createPost()
            } else {
                editPost()
            }
        }
    }

    private fun toggleButtonPostAvailable(boolean: Boolean) {
        if (boolean) {
            btnPostW10?.isEnabled = true
            btnPostW10?.setBackgroundResource(R.drawable.bg_button_post_enable)
        } else {
            btnPostW10?.isEnabled = false
            btnPostW10?.setBackgroundResource(R.drawable.bg_button_post_unable)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createPost() {
        val progressDialogLoadingBuilder =
            AlertDialog.Builder(context).setView(R.layout.progress_dialog_loading)
        val progressDialogLoading = progressDialogLoadingBuilder.create()

        val createPostBody = CreatePostBody(edtContentPostW10?.text.toString())
        val createPost = token?.let {
            RetrofitClient.createPostService()?.createPost(it, handleGetImageFile(), createPostBody)
        }

        progressDialogLoading.show()
        createPost?.enqueue(object : retrofit2.Callback<StatusResponse> {
            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                progressDialogLoading.dismiss()
                Toast.makeText(
                    context,
                    t.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                progressDialogLoading.dismiss()
                when (response.code()) {
                    HttpsURLConnection.HTTP_OK -> {
                        Toast.makeText(
                            context,
                            response.body()?.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        fragmentManager?.popBackStack()
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun editPost() {
        val progressDialogLoadingBuilder =
            AlertDialog.Builder(context).setView(R.layout.progress_dialog_loading)
        val progressDialogLoading = progressDialogLoadingBuilder.create()

        val createPostBody = CreatePostBody(edtContentPostW10?.text.toString())
        val editPost = token?.let {
            idEditPost?.let { postId ->
                RetrofitClient.createPostService()?.updatePost(
                    it, postId, handleGetImageFile(), createPostBody
                )
            }
        }

        progressDialogLoading.show()
        editPost?.enqueue(object : retrofit2.Callback<StatusResponse> {
            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                progressDialogLoading.dismiss()
                Toast.makeText(
                    context,
                    t.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                progressDialogLoading.dismiss()
                when (response.code()) {
                    HttpsURLConnection.HTTP_OK -> {
                        Toast.makeText(
                            context,
                            response.body()?.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        fragmentManager?.popBackStack()
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }

        })
    }

    private fun handleGetImageFile(): MultipartBody.Part? {
        return if (imageName != null) {
            null
        } else if (imageUri != null && imageUri.toString().isNotBlank()) {
            val file = File(getPath(imageUri))
            val image = file.asRequestBody(IMAGE_TYPE_KEY.toMediaTypeOrNull())
            MultipartBody.Part.createFormData(DEFAULT_NAME_IMAGE_UPLOAD, file.name, image)
        } else
        /*imageUri?.let {
                val file = File(getPath(it))
                val image = file.asRequestBody(IMAGE_TYPE_KEY.toMediaTypeOrNull())
                return MultipartBody.Part.createFormData(DEFAULT_NAME_IMAGE_UPLOAD, file.name, image)
            }*/
            null
    }

    private fun getPath(uri: Uri?): String {
        val result: String
        val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (cursor == null) {
            result = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            val data = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(data)
            cursor.close()
        }
        return result
    }

    private fun onChoseImageUploadFile() {
        context?.let { CropImage.activity().start(it, this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                imageUri = result.uri
                imgUploadImagePostW10?.setImageURI(imageUri)
                imageName = null
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val exception = result.error
                Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
