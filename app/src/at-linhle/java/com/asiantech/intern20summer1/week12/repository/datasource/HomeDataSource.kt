package com.asiantech.intern20summer1.week12.repository.datasource

import com.asiantech.intern20summer1.week12.models.LikeResponse
import com.asiantech.intern20summer1.week12.models.Post
import io.reactivex.Single
import retrofit2.Response

interface HomeDataSource {
    fun getListPost(token: String): Single<Response<MutableList<Post>>>?

    fun updateLikePost(token: String, id: Int): Single<Response<LikeResponse>>?
}
