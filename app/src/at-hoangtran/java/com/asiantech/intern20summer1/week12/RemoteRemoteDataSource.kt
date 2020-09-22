package com.asiantech.intern20summer1.week12

import io.reactivex.Single
import retrofit2.Response

class RemoteRemoteDataSource(private val api: ApiClient) : RemoteDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        api.createUserService()?.login(email, password)

    override fun addNewUser(userRegister: UserRegister): Single<Response<User>>? =
        api.createUserService()?.addNewUser(userRegister)


    override fun getListPost(token: String): Single<MutableList<Post>>? =
        api.createPostService()?.getListPost(token)

    override fun updatePostLike(token: String, id: Int): Single<Response<LikeResponse>>? =
        api.createPostService()?.updatePostLike(token, id)
}
