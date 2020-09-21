package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.model.User
import com.asiantech.intern20summer1.w12.model.UserRegister
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.remoteRepository.dataResource.RegisterDataResource
import io.reactivex.Single
import retrofit2.Response

class RegisterViewModel(private val repository: RemoteRepository) : ViewModel(),
    RegisterDataResource {
    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        repository.register(userRegister)
//    internal fun register(userRegister: UserRegister) =
//        APIClient.createUserService()?.addUser(userRegister)
}
