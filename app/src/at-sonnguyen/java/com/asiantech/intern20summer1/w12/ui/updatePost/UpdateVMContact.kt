package com.asiantech.intern20summer1.w12.ui.updatePost

import com.asiantech.intern20summer1.w12.data.model.PostContent
import com.asiantech.intern20summer1.w12.data.model.StatusResponse
import io.reactivex.Single
import okhttp3.MultipartBody

interface UpdateVMContact {
    fun updatePost(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ): Single<retrofit2.Response<StatusResponse>>?
}
