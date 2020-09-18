package com.example.demo_rx.viewModel

import androidx.lifecycle.ViewModel
import com.example.demo_rx.api.APIClient


class LoginViewModel : ViewModel() {
    internal fun login(email: String, password: String) =
        APIClient.createUserService()?.login(email, password)
}
