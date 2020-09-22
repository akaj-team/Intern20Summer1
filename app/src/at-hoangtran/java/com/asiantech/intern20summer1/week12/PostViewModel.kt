package com.asiantech.intern20summer1.week12

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import retrofit2.Response

class PostViewModel(private val remoteRepository: RemoteRepository) : ViewModel(), HomeDataSource {
    override fun getListPost(token: String): Single<MutableList<Post>>? =
        remoteRepository.getListPost(token)

    override fun updatePostLike(token: String, id: Int): Single<Response<LikeResponse>>? =
        remoteRepository.updatePostLike(token, id)
}
