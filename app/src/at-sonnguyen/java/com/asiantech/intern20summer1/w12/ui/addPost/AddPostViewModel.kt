package com.asiantech.intern20summer1.w12.ui.addPost

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.data.model.PostContent
import com.asiantech.intern20summer1.w12.data.model.StatusResponse
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class AddPostViewModel(private val repository: RemoteRepository) : ViewModel(),
    AddPostVMContact {
    override fun addPost(
        token: String,
        postContent: PostContent,
        image: MultipartBody.Part?
    ): Single<Response<StatusResponse>>? = repository.addPost(token, postContent, image)?.doOnSuccess {

    }
//    internal fun addPost(token : String, postContent: PostContent, image : MultipartBody.Part?) = APIClient.createPostService()?.createPost(token,postContent,image)
}