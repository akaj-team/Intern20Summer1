package com.asiantech.intern20summer1.week12.viewmodels

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.models.User
import com.asiantech.intern20summer1.week12.models.UserRegister
import com.asiantech.intern20summer1.week12.repository.datasource.LoginDataSource
import com.asiantech.intern20summer1.week12.repository.RemoteRepository
import io.reactivex.Single
import retrofit2.Response

class LoginViewModel(private val repository: RemoteRepository) : ViewModel(), LoginDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        repository.login(email, password)

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        repository.register(userRegister)
}
