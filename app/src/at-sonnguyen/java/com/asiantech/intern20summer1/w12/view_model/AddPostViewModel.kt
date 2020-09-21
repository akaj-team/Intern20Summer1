package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.model.PostContent
import com.asiantech.intern20summer1.w12.model.StatusResponse
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.remoteRepository.dataResource.AddPostDataResource
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class AddPostViewModel(private val repository: RemoteRepository) : ViewModel(),
    AddPostDataResource {
    override fun addPost(
        token: String,
        postContent: PostContent,
        image: MultipartBody.Part?
    ): Single<Response<StatusResponse>>? = repository.addPost(token, postContent, image)
//    internal fun addPost(token : String, postContent: PostContent, image : MultipartBody.Part?) = APIClient.createPostService()?.createPost(token,postContent,image)
}