package com.example.demo_rx.viewModel

import androidx.lifecycle.ViewModel
import com.example.demo_rx.api.APIClient
import com.example.demo_rx.model.UserRegister

class RegisterViewModel : ViewModel() {
    internal fun register(userRegister: UserRegister) =
        APIClient.createUserService()?.addUser(userRegister)
}
