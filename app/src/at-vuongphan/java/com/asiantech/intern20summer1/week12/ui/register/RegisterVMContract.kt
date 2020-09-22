package com.asiantech.intern20summer1.week12.ui.register

import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface RegisterVMContract {
    fun addUserRegister(userRegister: UserRegister): Single<Response<UserAutoSignIn>>?
}
