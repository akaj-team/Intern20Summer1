package com.asiantech.intern20summer1.w12.ui.login

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.network.APIClient
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import io.reactivex.Single
import retrofit2.Response


class LoginViewModel(private val repository: RemoteRepository) : ViewModel(), LoginVMContact {
    internal fun autoLogin(token: String?) = APIClient.createUserService()?.autoSignIn(token)

    override fun login(email: String, password: String): Single<Response<User>>? =
        repository.login(email, password)
}
