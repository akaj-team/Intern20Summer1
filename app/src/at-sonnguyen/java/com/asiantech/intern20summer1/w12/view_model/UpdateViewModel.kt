package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.model.PostContent
import com.asiantech.intern20summer1.w12.model.StatusResponse
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.remoteRepository.dataResource.UpdateDataResource
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class UpdateViewModel(private val repository: RemoteRepository) : ViewModel(), UpdateDataResource {
    override fun updatePost(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ): Single<Response<StatusResponse>>? = repository.updatePost(token, id, image, postContent)
}
