package com.asiantech.intern20summer1.w12.remoteRepository.dataResource

import com.asiantech.intern20summer1.w12.model.PostContent
import com.asiantech.intern20summer1.w12.model.StatusResponse
import io.reactivex.Single
import okhttp3.MultipartBody

interface UpdateDataResource {
    fun updatePost(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ): Single<retrofit2.Response<StatusResponse>>?
}
