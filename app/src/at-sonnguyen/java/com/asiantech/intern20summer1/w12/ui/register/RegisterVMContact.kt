package com.asiantech.intern20summer1.w12.ui.register

import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

interface RegisterVMContact {
    fun register(userRegister: UserRegister): Single<Response<User>>?

    fun isValidInfoStatus(): BehaviorSubject<Boolean>

    fun isValidInformation(email: String, fullNam: String, password: String)
}
