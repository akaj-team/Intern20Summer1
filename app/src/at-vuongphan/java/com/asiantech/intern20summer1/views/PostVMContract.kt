package com.asiantech.intern20summer1.views

import com.asiantech.intern20summer1.model.w10.NewPost
import com.asiantech.intern20summer1.model.w10.ResponseLike
import io.reactivex.Single
import retrofit2.Response

interface PostVMContract {
    fun getPost(token: String): Single<Response<MutableList<NewPost>>>?

    fun likePost(token: String, id: Int): Single<Response<ResponseLike>>?
}
