package com.asiantech.intern20summer1.week12.ui.home

import com.asiantech.intern20summer1.week12.data.source.datasource.PostDataSource
import com.asiantech.intern20summer1.week12.data.models.ApiResponse
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class PostViewModel(private val postRepository: PostDataSource) : PostVMContract {
    override fun getPost(token: String): Single<Response<MutableList<NewPost>>>? =
        postRepository.getPost(token)

    override fun likePost(token: String, id: Int): Single<Response<ResponseLike>>? =
        postRepository.likePost(token, id)

    override fun createNewPost(
        token: String,
        body: Post,
        image: MultipartBody.Part?
    ): Single<Response<ApiResponse>>? = postRepository.createNewPost(token, body, image)

}
