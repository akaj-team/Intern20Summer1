package com.example.demo_rx.viewModel

import androidx.lifecycle.ViewModel
import com.example.demo_rx.api.APIClient
import java.util.*

class HomeViewModel : ViewModel() {
    internal fun getAllPost(token: String) = APIClient.createPostService()?.getAllPost(token)

    internal fun likePost(token: String,id : Int) = APIClient.createPostService()?.likePost(token,id)
}

