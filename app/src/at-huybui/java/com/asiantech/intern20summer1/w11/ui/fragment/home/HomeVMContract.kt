package com.asiantech.intern20summer1.w11.ui.fragment.home

import android.net.Uri
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
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

    fun likePost(position: Int): Single<Response<ResponseLike>>?
    fun getDataAdapter(): MutableList<PostItem>
    fun getPostsData(): Single<Response<List<PostItem>>>?
    fun searchData(key: String): Single<Response<List<PostItem>>>?
    fun loadMoreData()
    fun getIdUser(): Int?
    fun createPost(content: String, uri: Uri?): Single<Response<ResponsePost>>?
    fun updatePost(idPost: Int, content: String, uri: Uri?): Single<Response<ResponsePost>>?
}
