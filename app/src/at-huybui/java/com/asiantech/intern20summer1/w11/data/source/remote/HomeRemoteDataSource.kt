package com.asiantech.intern20summer1.w11.data.source.remote

import com.asiantech.intern20summer1.w11.data.source.remote.network.ApiClient
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import com.asiantech.intern20summer1.w11.data.source.datasource.HomeDataSource
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is PostsRemoteDataSource
 */
class HomeRemoteDataSource : HomeDataSource {
    private val apiService = ApiClient.getApiService()
    override fun getPosts(token: String): Single<Response<List<PostItem>>>? =
        apiService?.getPostLists(token)

    override fun createPost(
        token: String,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Single<Response<ResponsePost>>? =
        apiService?.createPost(token, image, body)


    override fun updatePost(
        token: String,
        id: Int,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Single<Response<ResponsePost>>? =
        apiService?.updatePost(token, id, image, body)

    override fun likePost(token: String, id: Int): Single<Response<ResponseLike>>? =
        apiService?.likePost(token, id)
}
