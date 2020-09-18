package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.api.APIClient


class LoginViewModel : ViewModel() {
    internal fun login(email: String, password: String) =
        APIClient.createUserService()?.login(email, password)

    internal fun autoLogin(token : String?) = APIClient.createUserService()?.autoSignIn(token)
}
