package com.asiantech.intern20summer1.week12

class PostViewModel {
    internal fun getPost(token: String) =
        ApiClient.createPostService()?.getListPost(token)

    internal fun onHeartClick(token: String, id: Int) =
        ApiClient.createPostService()?.updatePostLike(token, id)
}
