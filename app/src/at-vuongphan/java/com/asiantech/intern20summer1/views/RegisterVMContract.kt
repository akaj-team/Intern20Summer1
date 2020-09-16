package com.asiantech.intern20summer1.views

import com.asiantech.intern20summer1.model.w10.UserAutoSignIn
import com.asiantech.intern20summer1.model.w10.UserRegister
import io.reactivex.Single
import retrofit2.Response

interface RegisterVMContract {
    fun addUserRegister(userRegister: UserRegister): Single<Response<UserAutoSignIn>>?
}
