package com.asiantech.intern20summer1.week12.viewmodels

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.api.ApiClient

class LoginViewModel : ViewModel() {
    internal fun login(email: String, password: String) =
        ApiClient.createUserService()?.login(email, password)
}
