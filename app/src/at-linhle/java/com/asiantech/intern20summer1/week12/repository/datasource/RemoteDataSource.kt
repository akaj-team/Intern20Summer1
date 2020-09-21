package com.asiantech.intern20summer1.week12.repository.datasource

import com.asiantech.intern20summer1.week12.models.LikeResponse
import com.asiantech.intern20summer1.week12.models.Post
import com.asiantech.intern20summer1.week12.models.User
import com.asiantech.intern20summer1.week12.models.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface RemoteDataSource {
    fun login(email: String, password: String): Single<Response<User>>?

    fun register(userRegister: UserRegister): Single<Response<User>>?

    fun getListPost(token : String): Single<Response<MutableList<Post>>>?

    fun updateLikePost(token: String, id:  Int): Single<Response<LikeResponse>>?
}
