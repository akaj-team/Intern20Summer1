package com.asiantech.intern20summer1.w12.remoteRepository.dataResource

import com.asiantech.intern20summer1.w12.model.User
import com.asiantech.intern20summer1.w12.model.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface RegisterDataResource {
    fun register(userRegister: UserRegister): Single<Response<User>>?
}
