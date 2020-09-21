package com.asiantech.intern20summer1.w12.remoteRepository

import com.asiantech.intern20summer1.w12.api.APIClient
import com.asiantech.intern20summer1.w12.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class RemoteRepository : RemoteDataSource {
    private val remoteRemoteDataSource = RemoteRemoteDataSource(APIClient)
    override fun login(email: String, password: String): Single<Response<User>>? =
        remoteRemoteDataSource.login(email, password)

    override fun getAllPosts(token: String): Single<Response<MutableList<Post>>>? =
        remoteRemoteDataSource.getAllPosts(token)

    override fun likePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        remoteRemoteDataSource.likePost(token, id)

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        remoteRemoteDataSource.register(userRegister)

    override fun updatePost(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ): Single<Response<StatusResponse>>? =
        remoteRemoteDataSource.updatePost(token, id, image, postContent)

    override fun addPost(
        token: String,
        postContent: PostContent,
        image: MultipartBody.Part?
    ): Single<Response<StatusResponse>>? = remoteRemoteDataSource.addPost(token, postContent, image)

}
