package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.api.APIClient

class HomeViewModel : ViewModel() {
    internal fun getAllPost(token: String) = APIClient.createPostService()?.getAllPost(token)

    internal fun likePost(token: String,id : Int) = APIClient.createPostService()?.likePost(token,id)
}

