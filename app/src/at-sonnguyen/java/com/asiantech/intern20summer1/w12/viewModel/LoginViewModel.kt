package com.asiantech.intern20summer1.w12.viewModel

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.api.APIClient


class LoginViewModel : ViewModel() {
    internal fun login(email: String, password: String) =
        APIClient.createUserService()?.login(email, password)
}
