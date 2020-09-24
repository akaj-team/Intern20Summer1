package com.asiantech.intern20summer1.w12.ui.login

import com.asiantech.intern20summer1.w12.data.model.User
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

interface LoginVMContact {
    fun login(email: String, password: String): Single<Response<User>>?

    fun isValidInformationStatus(): BehaviorSubject<Boolean>

    fun isValidInformation(email: String, password: String)
}
