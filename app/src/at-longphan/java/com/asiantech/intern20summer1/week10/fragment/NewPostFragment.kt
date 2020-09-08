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
        internal fun newInstance(token: String?) = NewPostFragment().apply {
            arguments = Bundle().apply {
                putString(TOKEN_KEY, token)
            }
        }

        internal const val IMAGE_TYPE_KEY = "image/*"
        private const val TOKEN_KEY = "token_key"
        private const val DEFAULT_NAME_IMAGE = "image"
    }

    private var token: String? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments?.getString(TOKEN_KEY)
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

        handleListeners()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleListeners() {

        imgBackNewPost?.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        imgUploadImagePostW10?.setOnClickListener {
            onChoseFile()
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
            createPost()
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

    private fun handleGetImageFile(): MultipartBody.Part? {
        imageUri?.let {
            val file = File(getPath(it))
            val image = file.asRequestBody(IMAGE_TYPE_KEY.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(DEFAULT_NAME_IMAGE, file.name, image)
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
            val data = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(data)
            cursor.close()
        }
        return result
    }

    private fun onChoseFile() {
        context?.let { CropImage.activity().start(it, this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                imageUri = result.originalUri
                imgUploadImagePostW10?.setImageURI(imageUri)
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val exception = result.error
                Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
