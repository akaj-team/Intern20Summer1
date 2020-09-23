package com.asiantech.intern20summer1.w11.ui.fragment.home

import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import io.reactivex.Single
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is HomeVMContract TODO("Not yet implemented").
 * It will TODO("Not yet implemented").
 */
interface HomeVMContract {

    fun likePost(token: String, position: Int): Single<Response<ResponseLike>>?
    fun getDataAdapter(): MutableList<PostItem>
    fun getPostsData(): Single<Response<List<PostItem>>>?
    fun searchData(key: String): Single<Response<List<PostItem>>>?
    fun loadMoreData()
    fun getIdUser(): Int?
    fun getToken(): String?
}
