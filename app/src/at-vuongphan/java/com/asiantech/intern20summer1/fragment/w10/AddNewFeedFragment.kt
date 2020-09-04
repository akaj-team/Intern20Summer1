package com.asiantech.intern20summer1.fragment.w10

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.api.ClientAPI
import kotlinx.android.synthetic.`at-vuongphan`.w10_add_new_feed.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class AddNewFeedFragment : Fragment() {
    companion object {
        var GALLERY_AVATAR_CODE = 101
    }

    var token: String? = null
    var imgPicture: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_add_new_feed, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            token = bundle.getString("token").toString()
        } else {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
        Log.d("TAG", "onCreateView: $token")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        circleImgAvatar.setOnClickListener {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), GALLERY_AVATAR_CODE
            )
        }
        initImageViewBack()
        createPost()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_AVATAR_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_AVATAR_CODE)
            return
        } else {
            if (!this.shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(context, "Please open permission on settings", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_AVATAR_CODE && resultCode == Activity.RESULT_OK) {
            imgPicture = data?.data
            circleImgAvatar.setImageURI(imgPicture)
        }
    }

    private fun createPost() {
        btnCreatePost.setOnClickListener {
            val file = File(imgPicture.toString())
            val content = """content"""
            val a = """this is post"""
            val c = "{$content : $a}"
            val requestBody = Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
                .toRequestBody("image/*".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("newImage", file.name, requestBody)
            val body = a.toRequestBody("text/plant".toMediaTypeOrNull())
            val call = token?.let { ClientAPI.createPost()?.createPost(it, part, body) }
            call?.enqueue(object : retrofit2.Callback<RequestBody> {
                override fun onResponse(call: Call<RequestBody>, response: Response<RequestBody>) {

                }

                override fun onFailure(call: Call<RequestBody>, t: Throwable) {

                }
            })
            (fragmentManager?.popBackStack())
        }
    }

    private fun initImageViewBack() {
        imgBackDetail?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}
