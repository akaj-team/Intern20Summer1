package com.asiantech.intern20summer1.week12.viewmodels

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.models.LikeResponse
import com.asiantech.intern20summer1.week12.models.Post
import com.asiantech.intern20summer1.week12.repository.RemoteRepository
import com.asiantech.intern20summer1.week12.repository.datasource.HomeDataSource
import io.reactivex.Single
import retrofit2.Response

class HomeViewModel(private val repository: RemoteRepository) : ViewModel(), HomeDataSource {
    override fun getListPost(token: String): Single<Response<MutableList<Post>>>? =
        repository.getListPost(token)

    override fun updateLikePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        repository.updateLikePost(token, id)
}
