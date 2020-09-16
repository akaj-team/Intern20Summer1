package com.asiantech.intern20summer1.views

import com.asiantech.intern20summer1.api.w10.datasource.PostDataSource
import com.asiantech.intern20summer1.model.w10.NewPost
import com.asiantech.intern20summer1.model.w10.ResponseLike
import io.reactivex.Single
import retrofit2.Response

class PostViewModel(private val postRepository: PostDataSource) : PostVMContract {
    override fun getPost(token: String): Single<Response<MutableList<NewPost>>>? =
        postRepository.getPost(token)

    override fun likePost(token: String, id: Int): Single<Response<ResponseLike>>? =
        postRepository.likePost(token, id)

}
