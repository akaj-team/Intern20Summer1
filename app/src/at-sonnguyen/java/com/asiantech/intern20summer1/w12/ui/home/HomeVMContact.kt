package com.asiantech.intern20summer1.w12.ui.home

import com.asiantech.intern20summer1.w12.data.model.LikeResponse
import com.asiantech.intern20summer1.w12.data.model.Post
import io.reactivex.Single
import retrofit2.Response

interface HomeVMContact {

    fun getAllPostsFromServer(token : String) : Single<Response<MutableList<Post>>>?

    fun likePost(token: String,id : Int) : Single<Response<LikeResponse>>?

    fun getPostList() : MutableList<Post?>

    fun getPostListAdapter() : MutableList<Post?>

    fun refreshData()

    fun likePost(token : String,id: Int,position : Int) : Single<Response<LikeResponse>>?

    fun loadMore(visibleItemCount: Int, totalItemCount: Int, firstVisibleItem: Int,token: String)

    fun getExtraPost()

    fun getAllPostFromServer() : MutableList<Post>
}
