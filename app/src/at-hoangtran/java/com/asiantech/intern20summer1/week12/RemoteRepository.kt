package com.asiantech.intern20summer1.week12

import io.reactivex.Single
import retrofit2.Response

class RemoteRepository : RemoteDataSource {
    private val remoteRemoteDataSource: RemoteRemoteDataSource? = null

    override fun login(email: String, password: String): Single<Response<User>>? =
        remoteRemoteDataSource?.login(email, password)

    override fun addNewUser(userRegister: UserRegister): Single<Response<User>>? =
        remoteRemoteDataSource?.addNewUser(userRegister)

    override fun getListPost(token: String): Single<MutableList<Post>>? =
        remoteRemoteDataSource?.getListPost(token)

    override fun updatePostLike(token: String, id: Int): Single<Response<LikeResponse>>? =
        remoteRemoteDataSource?.updatePostLike(token, id)
}
