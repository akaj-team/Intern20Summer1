package com.asiantech.intern20summer1.w12.ui.addPost

import com.asiantech.intern20summer1.w12.data.model.PostContent
import com.asiantech.intern20summer1.w12.data.model.StatusResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

interface AddPostVMContact {
    fun addPost(
        token: String,
        postContent: PostContent,
        image: MultipartBody.Part?
    ): Single<Response<StatusResponse>>?
}
