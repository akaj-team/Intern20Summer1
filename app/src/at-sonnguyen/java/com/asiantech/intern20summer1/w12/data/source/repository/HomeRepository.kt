package com.asiantech.intern20summer1.w12.data.source.repository

import com.asiantech.intern20summer1.w12.data.model.LikeResponse
import com.asiantech.intern20summer1.w12.data.model.Post
import com.asiantech.intern20summer1.w12.data.source.datasource.HomeDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.HomeRemoteDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.network.APIClient
import io.reactivex.Single
import retrofit2.Response

class HomeRepository : HomeDataSource {
    private val homeRemoteRepository = HomeRemoteDataSource(APIClient)
    override fun getAllPosts(token: String): Single<Response<MutableList<Post>>>? =
        homeRemoteRepository.getAllPosts(token)

    override fun likePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        homeRemoteRepository.likePost(token, id)
}
