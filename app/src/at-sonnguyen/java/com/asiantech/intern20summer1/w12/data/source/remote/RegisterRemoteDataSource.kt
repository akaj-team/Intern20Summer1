package com.asiantech.intern20summer1.w12.data.source.remote

import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import com.asiantech.intern20summer1.w12.data.source.datasource.RegisterDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.network.APIClient
import io.reactivex.Single
import retrofit2.Response

class RegisterRemoteDataSource(private val api: APIClient) : RegisterDataSource {
    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        api.createService()?.addUser(userRegister)
}
