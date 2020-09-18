package com.example.demo_rx.viewModel

import androidx.lifecycle.ViewModel
import com.example.demo_rx.api.APIClient
import com.example.demo_rx.model.PostContent
import okhttp3.MultipartBody

class AddPostViewModel : ViewModel(){
    internal fun addPost(token : String,postContent: PostContent,image : MultipartBody.Part?) = APIClient.createPostService()?.createPost(token,postContent,image)
}