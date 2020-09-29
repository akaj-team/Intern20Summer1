package com.asiantech.intern20summer1.w12.ui.home

import com.asiantech.intern20summer1.w12.data.model.LikeResponse
import com.asiantech.intern20summer1.w12.data.model.Post
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

interface HomeVMContact {

    fun getAllPostsFromServer(token: String): Single<Response<MutableList<Post>>>?

    fun getPostList(): MutableList<Post?>

    fun refreshData()

    fun likePost(token: String, id: Int, position: Int): Single<Response<LikeResponse>>?

    fun loadMore(lastVisibleItem: Int)

    fun updateProgressBar() : BehaviorSubject<Boolean>

    fun getAllPostFromServer(): MutableList<Post>

    fun isEnableProgressBar(): BehaviorSubject<Boolean>

    fun searchContent(key: String, token: String): Single<Response<MutableList<Post>>>?

    fun isSearching(): Boolean
}
