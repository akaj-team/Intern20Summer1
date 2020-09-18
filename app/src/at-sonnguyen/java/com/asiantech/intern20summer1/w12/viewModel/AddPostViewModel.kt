package com.asiantech.intern20summer1.w12.viewModel

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.api.APIClient
import com.asiantech.intern20summer1.w12.model.PostContent
import okhttp3.MultipartBody

class AddPostViewModel : ViewModel(){
    internal fun addPost(token : String, postContent: PostContent, image : MultipartBody.Part?) = APIClient.createPostService()?.createPost(token,postContent,image)
}