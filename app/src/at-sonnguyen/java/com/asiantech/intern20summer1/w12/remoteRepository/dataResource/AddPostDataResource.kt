package com.asiantech.intern20summer1.w12.remoteRepository.dataResource

import com.asiantech.intern20summer1.w12.model.PostContent
import com.asiantech.intern20summer1.w12.model.StatusResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

interface AddPostDataResource {
    fun addPost(
        token: String,
        postContent: PostContent,
        image: MultipartBody.Part?
    ): Single<Response<StatusResponse>>?
}
