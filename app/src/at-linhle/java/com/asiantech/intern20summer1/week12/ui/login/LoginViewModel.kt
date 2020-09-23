package com.asiantech.intern20summer1.week12.ui.login

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import com.asiantech.intern20summer1.week12.data.source.LoginRepository
import com.asiantech.intern20summer1.week12.data.source.datasource.LoginDataSource
import io.reactivex.Single
import retrofit2.Response

class LoginViewModel(private val repository: LoginRepository) : ViewModel(),
    LoginDataSource {
    override fun login(email: String, password: String): Single<Response<User>>? =
        repository.login(email, password)

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        repository.register(userRegister)
}
