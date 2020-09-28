package com.asiantech.intern20summer1.week12.data.source.remote

import com.asiantech.intern20summer1.week12.data.model.LikeResponse
import com.asiantech.intern20summer1.week12.data.model.Post
import com.asiantech.intern20summer1.week12.data.source.datasource.HomeDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.network.ApiClient
import com.asiantech.intern20summer1.week12.data.source.remote.network.ApiService
import io.reactivex.Single
import retrofit2.Response

class HomeRemoteDataSource(private val api: ApiService? = ApiClient.createService() ) : HomeDataSource {
    override fun getListPost(token: String): Single<Response<MutableList<Post>>>? =
        api?.getListPost(token)

    override fun updateLikePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        api?.updatePostLike(token, id)
}
