package com.asiantech.intern20summer1.week12

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import retrofit2.Response

class LoginViewModel(private val remoteRepository: RemoteRepository) : ViewModel(),
    LoginDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        remoteRepository.login(email, password)
}
