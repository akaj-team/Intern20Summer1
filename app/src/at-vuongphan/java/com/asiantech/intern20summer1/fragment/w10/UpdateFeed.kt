package com.asiantech.intern20summer1.fragment.w10

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.RecyclerViewNewFeed
import com.asiantech.intern20summer1.api.w10.ClientAPI
import com.asiantech.intern20summer1.model.w10.ApiResponse
import com.asiantech.intern20summer1.model.w10.Post
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.w10_update_new_feed.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class UpdateFeed : Fragment() {
    companion object {
        var token: String? = null
        var idUpdate: Int? = null
        private var url = "https://at-a-trainning.000webhostapp.com/images/"
        private var newImage: String? = null
        var content: String? = null
        var image: String? = null
        var imgPicture: Uri? = null

        internal fun newInstance() = UpdateFeed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_update_new_feed, container, false)
        getDataUpdate()
        view.findViewById<EditText>(R.id.edtNoidung).setText(content)
        Log.d("TAG", "mmmm ${url.plus(image)}")
        Glide.with(view)
            .load(url.plus(image))
            .into(view.findViewById<CircleImageView>(R.id.circleImgAvatar))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImageViewBack()
        initAvatarOnclick()
        updatePost()
    }

    private fun updatePost() {
        btnCreatePost?.setOnClickListener {
            if (imgPicture == null) {
                imgPicture = Uri.parse(url.plus(image))
            }
            Log.d("TAG", "updatePost: $imgPicture")
            val newId = idUpdate
            val newContent = edtNoidung.text.toString()
            val file = File(getPath(imgPicture))
            val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("image", file.name, fileReqBody)
            val call =
                token?.let { it1 ->
                    newId.let { it2 ->
                        it2?.let { it3 ->
                            ClientAPI.createPost()?.updatePost(
                                it1,
                                it3, Post(newContent), part
                            )
                        }
                    }
                }
            call?.enqueue(object : retrofit2.Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

                }
            })
            (activity as? RecyclerViewNewFeed)?.openFragment(NewFeedFragment.newInstance())
        }
    }

    private fun initImageViewBack() {
        imgBackDetail?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun initAvatarOnclick() {
        circleImgAvatar.setOnClickListener {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), AddNewFeedFragment.GALLERY_AVATAR_CODE
            )
        }
    }

    private fun getDataUpdate() {
        val bundle = this.arguments
        if (bundle != null) {
            token = bundle.getString("token").toString()
            idUpdate = bundle.getInt("id")
            content = bundle.getString("content")
            image = bundle.getString("image")
            Log.d("TAG", "getDataUpdate: $idUpdate")
        } else {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPath(uri: Uri?): String? {
        val result: String
        val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (cursor == null) {
            result = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AddNewFeedFragment.GALLERY_AVATAR_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, AddNewFeedFragment.GALLERY_AVATAR_CODE)
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
        if (requestCode == AddNewFeedFragment.GALLERY_AVATAR_CODE && resultCode == Activity.RESULT_OK) {
            imgPicture = data?.data
            Log.d("TAG", "image:$imgPicture")
            circleImgAvatar.setImageURI(imgPicture)
        }
    }
}
