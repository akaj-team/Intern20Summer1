package com.asiantech.intern20summer1.w12.ui.register

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import io.reactivex.Single
import retrofit2.Response

class RegisterViewModel(private val repository: RemoteRepository) : ViewModel(),
    RegisterVMContact {
    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        repository.register(userRegister)?.doOnSuccess {

        }
//    internal fun register(userRegister: UserRegister) =
//        APIClient.createUserService()?.addUser(userRegister)
}
