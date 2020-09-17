package com.asiantech.intern20summer1.w11.ui.viewmodel

import com.asiantech.intern20summer1.w11.data.datasource.PostsDataSource
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is HomeViewModel TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */

class HomeViewModel(private val postsDataSource: PostsDataSource) : HomeViewModelContract {

    override fun getPosts(token: String): Observable<Response<List<PostItem>>>? =
        postsDataSource.getPosts(token)

    override fun createPost(
        token: String,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Observable<Response<ResponsePost>>? =
        postsDataSource.createPost(token, image, body)

    override fun updatePost(
        token: String,
        id: Int,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Observable<Response<ResponsePost>>? =
        postsDataSource.updatePost(token, id, image, body)

    override fun likePost(token: String, id: Int): Observable<Response<ResponseLike>>? =
        postsDataSource.likePost(token, id)
}
     