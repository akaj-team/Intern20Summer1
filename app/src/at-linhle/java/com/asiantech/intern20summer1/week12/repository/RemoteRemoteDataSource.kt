package com.asiantech.intern20summer1.week12.repository

import com.asiantech.intern20summer1.week12.api.ApiClient
import com.asiantech.intern20summer1.week12.models.LikeResponse
import com.asiantech.intern20summer1.week12.models.Post
import com.asiantech.intern20summer1.week12.models.User
import com.asiantech.intern20summer1.week12.models.UserRegister
import com.asiantech.intern20summer1.week12.repository.datasource.RemoteDataSource
import io.reactivex.Single
import retrofit2.Response

class RemoteRemoteDataSource(private val api: ApiClient) : RemoteDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        api.createUserService()?.login(email, password)

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        api.createUserService()?.addNewUser(userRegister)

    override fun getListPost(token: String): Single<Response<MutableList<Post>>>? = api.createPostService()?.getListPost(token)

    override fun updateLikePost(token: String, id: Int): Single<Response<LikeResponse>>? = api.createPostService()?.updatePostLike(token, id)
}
