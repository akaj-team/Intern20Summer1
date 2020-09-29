package com.asiantech.intern20summer1.w12.data.source.remote

import com.asiantech.intern20summer1.w12.data.model.LikeResponse
import com.asiantech.intern20summer1.w12.data.model.Post
import com.asiantech.intern20summer1.w12.data.source.datasource.HomeDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.network.APIClient
import com.asiantech.intern20summer1.w12.data.source.remote.network.APIService
import io.reactivex.Single
import retrofit2.Response

class HomeRemoteDataSource(private val api: APIService? = APIClient.createService()) : HomeDataSource {
    override fun getAllPosts(token: String): Single<Response<MutableList<Post>>>? =
        api?.getAllPost(token)

    override fun likePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        api?.likePost(token, id)
}
