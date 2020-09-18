package com.asiantech.intern20summer1.w11.ui.viewmodel

import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is HomeViewModelContract TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
interface HomeViewModelContract {
    fun getPosts(token: String): Observable<Response<List<PostItem>>>?
    fun createPost(
        token: String,
        image: MultipartBody.Part? = null,
        body: RequestBody
    ): Observable<Response<ResponsePost>>?

    fun updatePost(
        token: String,
        id: Int = 0,
        image: String?,
        body: RequestBody
    ): Single<Response<ResponsePost>>?

    fun likePost(token: String, id: Int): Observable<Response<ResponseLike>>?
}
     