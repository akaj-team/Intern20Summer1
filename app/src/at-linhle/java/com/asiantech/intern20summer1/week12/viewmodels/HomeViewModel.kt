package com.asiantech.intern20summer1.week12.viewmodels

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.api.ApiClient


class HomeViewModel : ViewModel() {
    internal fun getListPost(token: String) = ApiClient.cretePostService()?.getListPost(token)

    internal fun updatePostLike(token: String, postId: Int) =
        ApiClient.cretePostService()?.updatePostLike(token, postId)
}
