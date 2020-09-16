package com.asiantech.intern20summer1.api.w10.remotedatassource

import com.asiantech.intern20summer1.api.w10.ClientAPI
import com.asiantech.intern20summer1.api.w10.datasource.PostDataSource
import com.asiantech.intern20summer1.model.w10.NewPost
import com.asiantech.intern20summer1.model.w10.ResponseLike
import io.reactivex.Single
import retrofit2.Response

class PostRemoteDataSource : PostDataSource {
    val callRxPost = ClientAPI.createPost()
    override fun getPost(token: String): Single<Response<MutableList<NewPost>>>? =
        callRxPost?.getPost(token)

    override fun likePost(token: String, id: Int): Single<Response<ResponseLike>>? =
        callRxPost?.likePost(token, id)
}
