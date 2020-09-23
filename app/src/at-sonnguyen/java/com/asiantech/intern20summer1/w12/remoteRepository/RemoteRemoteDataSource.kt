package com.asiantech.intern20summer1.w12.remoteRepository

import com.asiantech.intern20summer1.w12.data.model.*
import com.asiantech.intern20summer1.w12.data.network.APIClient
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class RemoteRemoteDataSource(private val api: APIClient) : RemoteDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        api.createUserService()?.login(email, password)

    override fun getAllPosts(token: String): Single<Response<MutableList<Post>>>? =
        api.createPostService()?.getAllPost(token)

    override fun likePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        api.createPostService()?.likePost(token,id)

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        api.createUserService()?.addUser(userRegister)

    override fun updatePost(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ): Single<Response<StatusResponse>>? =
        api.createPostService()?.updatePost(token, id, image, postContent)

    override fun addPost(
        token: String,
        postContent: PostContent,
        image: MultipartBody.Part?
    ): Single<Response<StatusResponse>>? =
        api.createPostService()?.createPost(token, postContent, image)
}
