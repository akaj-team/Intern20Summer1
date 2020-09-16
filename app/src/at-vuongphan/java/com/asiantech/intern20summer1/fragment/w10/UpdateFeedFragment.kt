package com.asiantech.intern20summer1.fragment.w10

import androidx.fragment.app.Fragment

class UpdateFeedFragment : Fragment() {
//    companion object {
//        var token: String? = null
//        var idUpdate: Int? = null
//        private var url = "https://at-a-trainning.000webhostapp.com/images/"
//        var content: String? = null
//        var image: String? = null
//        var imgPicture: Uri? = null
//
//        internal fun newInstance() = UpdateFeedFragment()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.w10_update_new_feed, container, false)
//        getDataUpdate()
//        view.findViewById<EditText>(R.id.edtNoidung).setText(content)
//        Glide.with(view)
//            .load(url.plus(image))
//            .placeholder(R.drawable.nu_xinh)
//            .into(view.findViewById<CircleImageView>(R.id.circleImgAvatar))
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initImageViewBack()
//        initAvatarOnclick()
//        updatePost()
//    }
//
//    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//    private fun updatePost() {
//        btnCreatePost?.setOnClickListener {
//            if (imgPicture == null) {
//                imgPicture = Uri.parse(url.plus(image))
//            }
//            val newId = idUpdate
//            val newContent = edtNoidung.text.toString()
//            val file = File(getPath(imgPicture))
//            val fileReqBody =
//                file.asRequestBody(resources.getString(R.string.string_gallery).toMediaTypeOrNull())
//            val part = MultipartBody.Part.createFormData(
//                resources.getString(R.string.string_image),
//                file.name,
//                fileReqBody
//            )
//            val call =
//                token?.let { it1 ->
//                    newId.let { it2 ->
//                        it2?.let { it3 ->
//                            ClientAPI.createPost()?.updatePost(
//                                it1,
//                                it3, Post(newContent), part
//                            )
//                        }
//                    }
//                }
//            call?.enqueue(object : retrofit2.Callback<ApiResponse> {
//                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//
//                }
//
//                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//
//                }
//            })
//            (activity as? RecyclerViewNewFeed)?.openFragment(NewFeedFragment.newInstance())
//        }
//    }
//
//    private fun initImageViewBack() {
//        imgBackDetail?.setOnClickListener {
//            fragmentManager?.popBackStack()
//        }
//    }
//
//    private fun initAvatarOnclick() {
//        circleImgAvatar.setOnClickListener {
//            requestPermissions(
//                arrayOf(
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ), AddNewFeedFragment.GALLERY_AVATAR_CODE
//            )
//        }
//    }
//
//    private fun getDataUpdate() {
//        val bundle = this.arguments
//        if (bundle != null) {
//            token = bundle.getString(resources.getString(R.string.key_token)).toString()
//            idUpdate = bundle.getInt(resources.getString(R.string.key_id))
//            content = bundle.getString(resources.getString(R.string.key_content))
//            image = bundle.getString(resources.getString(R.string.key_image_api))
//        } else {
//            Toast.makeText(
//                requireContext(),
//                resources.getString(R.string.string_error),
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    private fun getPath(uri: Uri?): String? {
//        val result: String
//        val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
//        if (cursor == null) {
//            result = uri?.path.toString()
//        } else {
//            cursor.moveToFirst()
//            @Suppress("DEPRECATION") val idx =
//                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//            result = cursor.getString(idx)
//            cursor.close()
//        }
//        return result
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == AddNewFeedFragment.GALLERY_AVATAR_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.type = resources.getString(R.string.string_gallery)
//            startActivityForResult(intent, AddNewFeedFragment.GALLERY_AVATAR_CODE)
//            return
//        } else {
//            if (!this.shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                Toast.makeText(
//                    context,
//                    resources.getString(R.string.denied_permission_gallery),
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == AddNewFeedFragment.GALLERY_AVATAR_CODE && resultCode == Activity.RESULT_OK) {
//            imgPicture = data?.data
//            circleImgAvatar.setImageURI(imgPicture)
//        }
//    }
}
