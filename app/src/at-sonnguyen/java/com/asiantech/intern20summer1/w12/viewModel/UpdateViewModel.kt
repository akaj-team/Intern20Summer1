package com.example.demo_rx.viewModel

import androidx.lifecycle.ViewModel
import com.example.demo_rx.api.APIClient
import com.example.demo_rx.model.PostContent
import okhttp3.MultipartBody

class UpdateViewModel : ViewModel() {
    internal fun update(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ) = APIClient.createPostService()?.updatePost(token, id, image, postContent)
}
