package com.asiantech.intern20summer1.week12

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    internal fun login(email: String, password: String) =
        ApiClient.createUserService()?.login(email, password)

    private fun register(userRegister: UserRegister) =
        ApiClient.createUserService()?.addNewUser(userRegister)
}
