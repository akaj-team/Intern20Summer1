package com.asiantech.intern20summer1.week12.ui.post

import com.asiantech.intern20summer1.week12.data.model.LikeResponse
import com.asiantech.intern20summer1.week12.data.model.Post
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

interface HomeVMContract {

    fun getListPost(): MutableList<Post?>

    fun getListPostFromServer(token: String): Single<Response<MutableList<Post>>>?

    fun updateLikePost(token: String, id: Int, position : Int): Single<Response<LikeResponse>>?

    fun refreshData()

    fun loadMore(lastVisibleItem: Int)

    fun updateProgressBar(): BehaviorSubject<Boolean>

    fun searchPostFromServer(key : String): Single<Response<MutableList<Post>>>?

    fun getResultSearch(): Boolean
}
