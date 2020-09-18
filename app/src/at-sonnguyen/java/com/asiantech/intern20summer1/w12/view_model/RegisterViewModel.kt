package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.api.APIClient
import com.asiantech.intern20summer1.w12.model.UserRegister

class RegisterViewModel : ViewModel() {
    internal fun register(userRegister: UserRegister) =
        APIClient.createUserService()?.addUser(userRegister)
}
