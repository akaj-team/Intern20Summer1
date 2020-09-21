package com.asiantech.intern20summer1.week12.repository

import com.asiantech.intern20summer1.week12.api.ApiClient
import com.asiantech.intern20summer1.week12.models.LikeResponse
import com.asiantech.intern20summer1.week12.models.Post
import com.asiantech.intern20summer1.week12.models.User
import com.asiantech.intern20summer1.week12.models.UserRegister
import com.asiantech.intern20summer1.week12.repository.datasource.RemoteDataSource
import io.reactivex.Single
import retrofit2.Response

class RemoteRepository: RemoteDataSource {
    private val remoteRemoteDataSource = RemoteRemoteDataSource(ApiClient)

    override fun login(email: String, password: String): Single<Response<User>>? = remoteRemoteDataSource.login(email, password)

    override fun register(userRegister: UserRegister): Single<Response<User>>? = remoteRemoteDataSource.register(userRegister)

    override fun getListPost(token: String): Single<Response<MutableList<Post>>>? = remoteRemoteDataSource.getListPost(token)

    override fun updateLikePost(token: String, id: Int): Single<Response<LikeResponse>>? = remoteRemoteDataSource.updateLikePost(token, id)
}
