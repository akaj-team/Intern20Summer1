package com.asiantech.intern20summer1.week12.data.source

import com.asiantech.intern20summer1.week12.data.models.ApiResponse
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import com.asiantech.intern20summer1.week12.data.source.datasource.PostDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.PostRemoteDataSource
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
