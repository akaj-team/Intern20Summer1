@file:Suppress("NAME_SHADOWING")

package com.asiantech.intern20summer1.week12.ui.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.activity.RecyclerViewNewFeed
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.source.PostRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-vuongphan`.w10_add_new_feed.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddNewFeedFragment : Fragment() {
    companion object {
        var GALLERY_AVATAR_CODE = 101
        internal fun newInstance() = AddNewFeedFragment()
    }

    var token: String? = null
    var imgPicture: Uri? = null
    private var viewModel: PostViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = PostViewModel(PostRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_add_new_feed, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            token = bundle.getString(resources.getString(R.string.key_token)).toString()
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.string_error),
                Toast.LENGTH_SHORT
            ).show()
        }
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
        createNewPost()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_AVATAR_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = resources.getString(R.string.string_gallery)
            startActivityForResult(intent, GALLERY_AVATAR_CODE)
            return
        } else {
            if (!this.shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.denied_permission_gallery),
                    Toast.LENGTH_SHORT
                )
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

    private fun initImageViewBack() {
        imgBackDetail.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun createNewPost() {
        btnCreatePost?.setOnClickListener {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val file =
                File(getPath(imgPicture))
            val fileReqBody =
                file.asRequestBody(resources.getString(R.string.string_gallery).toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData(
                resources.getString(R.string.string_image),
                file.name,
                fileReqBody
            )
            val content = edtNoidung.text.toString()
            token?.let { it1 -> viewModel?.createNewPost(it1, Post(content), part) }
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ it ->
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
                    }
                }, {
                    it.message
                })
            (activity as? RecyclerViewNewFeed)?.openFragment(NewFeedFragment.newInstance())
        }
    }

    private fun getPath(uri: Uri?): String? {
        val result: String
        val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (cursor == null) {
            result = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            @Suppress("DEPRECATION") val idx =
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}
