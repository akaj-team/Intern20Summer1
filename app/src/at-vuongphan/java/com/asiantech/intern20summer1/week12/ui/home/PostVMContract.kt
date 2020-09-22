package com.asiantech.intern20summer1.week12.ui.home

import com.asiantech.intern20summer1.week12.data.models.ApiResponse
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

interface PostVMContract {
    fun getPost(token: String): Single<Response<MutableList<NewPost>>>?

    fun likePost(token: String, id: Int): Single<Response<ResponseLike>>?

    fun createNewPost(
        token: String,
        body: Post,
        image: MultipartBody.Part?
    ): Single<Response<ApiResponse>>?
}
