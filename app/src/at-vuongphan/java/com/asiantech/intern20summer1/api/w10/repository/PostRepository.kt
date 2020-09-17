package com.asiantech.intern20summer1.api.w10.repository

import com.asiantech.intern20summer1.api.w10.datasource.PostDataSource
import com.asiantech.intern20summer1.api.w10.remotedatassource.PostRemoteDataSource
import com.asiantech.intern20summer1.model.w10.ApiResponse
import com.asiantech.intern20summer1.model.w10.NewPost
import com.asiantech.intern20summer1.model.w10.Post
import com.asiantech.intern20summer1.model.w10.ResponseLike
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class PostRepository : PostDataSource {
    private val postRemoteDataSource = PostRemoteDataSource()
    override fun getPost(token: String): Single<Response<MutableList<NewPost>>>? =
        postRemoteDataSource.getPost(token)

    override fun likePost(token: String, id: Int): Single<Response<ResponseLike>>? =
        postRemoteDataSource.likePost(token, id)

    override fun createNewPost(
        token: String,
        body: Post,
        image: MultipartBody.Part?
    ): Single<Response<ApiResponse>>? = postRemoteDataSource.createNewPost(token, body, image)
}
