package com.asiantech.intern20summer1.w12.data.source.repository

import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import com.asiantech.intern20summer1.w12.data.source.datasource.RegisterDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.RegisterRemoteDataSource
import com.asiantech.intern20summer1.w12.data.source.remote.network.APIClient
import io.reactivex.Single
import retrofit2.Response

class RegisterRepository : RegisterDataSource {
    private val registerRemoteRepository = RegisterRemoteDataSource(APIClient)

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        registerRemoteRepository.register(userRegister)
}
