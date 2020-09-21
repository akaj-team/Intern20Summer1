package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.model.LikeResponse
import com.asiantech.intern20summer1.w12.model.Post
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.remoteRepository.dataResource.HomeDataSource
import io.reactivex.Single
import retrofit2.Response

class HomeViewModel(private val repository: RemoteRepository) : ViewModel(),HomeDataSource {
    override fun getAllPosts(token: String): Single<Response<MutableList<Post>>>? = repository.getAllPosts(token)

    override fun likePost(token: String, id: Int): Single<Response<LikeResponse>>? =repository.likePost(token, id)
//    internal fun getAllPost(token: String) = APIClient.createPostService()?.getAllPost(token)
//
//    internal fun likePost(token: String,id : Int) = APIClient.createPostService()?.likePost(token,id)
}
