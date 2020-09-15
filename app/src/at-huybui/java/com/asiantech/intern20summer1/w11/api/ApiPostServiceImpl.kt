package com.asiantech.intern20summer1.w11.api

import com.asiantech.intern20summer1.w11.models.PostItem
import com.asiantech.intern20summer1.w11.models.ResponseLike
import com.asiantech.intern20summer1.w11.models.ResponsePost
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 14/09/2020.
 * This is ApiAccountServiceImpl TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class ApiPostServiceImpl : ApiPostService {

    override fun createPost(
        token: String,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Observable<ResponsePost> {
        TODO("Not yet implemented")
    }

    override fun updatePost(
        token: String,
        id: Int,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Observable<ResponsePost> {
        TODO("Not yet implemented")
    }

    override fun deletePost(token: String, id: Int): Observable<ResponsePost> {
        TODO("Not yet implemented")
    }

    override fun likePost(token: String, id: Int): Observable<ResponseLike> {
        TODO("Not yet implemented")
    }

    override fun getPostLists(token: String): Observable<List<PostItem>> {
        return Rx2AndroidNetworking.get(ApiPostService.PAST_GET_POSTS).build()
            .getObjectListObservable(PostItem::class.java)
    }
}
     