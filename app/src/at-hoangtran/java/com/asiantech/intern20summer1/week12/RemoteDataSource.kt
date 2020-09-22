package com.asiantech.intern20summer1.week12

import io.reactivex.Single
import retrofit2.Response

interface RemoteDataSource {
    fun login(email: String, password: String): Single<Response<User>>?

    fun addNewUser(userRegister: UserRegister): Single<Response<User>>?

    fun getListPost(token: String): Single<MutableList<Post>>?

    fun updatePostLike(token: String, id: Int): Single<Response<LikeResponse>>?
}
