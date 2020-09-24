package com.asiantech.intern20summer1.w12.data.source.datasource

import com.asiantech.intern20summer1.w12.data.model.LikeResponse
import com.asiantech.intern20summer1.w12.data.model.Post
import io.reactivex.Single
import retrofit2.Response

interface HomeDataSource {
    fun getAllPosts(token: String): Single<Response<MutableList<Post>>>?

    fun likePost(token: String, id: Int): Single<Response<LikeResponse>>?
}
