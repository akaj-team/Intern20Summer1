package com.asiantech.intern20summer1.w12.remoteRepository

import com.asiantech.intern20summer1.w12.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

interface RemoteDataSource {

    fun login(email: String, password: String): Single<Response<User>>?

    fun getAllPosts(token: String): Single<Response<MutableList<Post>>>?

    fun likePost(token: String, id: Int): Single<Response<LikeResponse>>?

    fun register(userRegister: UserRegister): Single<Response<User>>?

    fun updatePost(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ) : Single<retrofit2.Response<StatusResponse>>?

    fun addPost(
        token: String,
        postContent: PostContent,
        image: MultipartBody.Part?
    ): Single<Response<StatusResponse>>?
}
