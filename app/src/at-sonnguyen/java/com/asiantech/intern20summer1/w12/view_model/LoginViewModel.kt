package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.api.APIClient
import com.asiantech.intern20summer1.w12.model.User
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.remoteRepository.dataResource.LoginDataSource
import io.reactivex.Single
import retrofit2.Response


class LoginViewModel(private val repository: RemoteRepository) : ViewModel(), LoginDataSource {
    internal fun autoLogin(token: String?) = APIClient.createUserService()?.autoSignIn(token)

    override fun login(email: String, password: String): Single<Response<User>>? =
        repository.login(email, password)
}
