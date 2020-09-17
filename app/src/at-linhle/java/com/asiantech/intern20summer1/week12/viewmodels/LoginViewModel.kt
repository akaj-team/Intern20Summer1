package com.asiantech.intern20summer1.week12.viewmodels

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.api.ApiClient
import com.asiantech.intern20summer1.week12.models.UserRegister

class LoginViewModel : ViewModel() {
    internal fun login(email: String, password: String) =
        ApiClient.createUserService()?.login(email, password)

    internal fun register(userRegister: UserRegister) = ApiClient.createUserService()?.addNewUser(userRegister)
}
