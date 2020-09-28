package com.asiantech.intern20summer1.week12.data.source

import com.asiantech.intern20summer1.week12.data.model.LikeResponse
import com.asiantech.intern20summer1.week12.data.model.Post
import com.asiantech.intern20summer1.week12.data.source.datasource.HomeDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.HomeRemoteDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.network.ApiClient
import io.reactivex.Single
import retrofit2.Response

class HomeRepository : HomeDataSource {
    private val homeRemoteDataSource = HomeRemoteDataSource(ApiClient.createService())

    override fun getListPost(token: String): Single<Response<MutableList<Post>>>? =
        homeRemoteDataSource.getListPost(token)

    override fun updateLikePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        homeRemoteDataSource.updateLikePost(token, id)
}
