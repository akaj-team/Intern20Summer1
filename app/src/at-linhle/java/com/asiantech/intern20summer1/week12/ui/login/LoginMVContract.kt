package com.asiantech.intern20summer1.week12.ui.login

import com.asiantech.intern20summer1.week12.data.model.User
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

interface LoginMVContract {
    fun login(email: String, password: String) : Single<Response<User>>?

    fun infoValidateStatus(): BehaviorSubject<Boolean>

    fun validateLoginInformation(email: String, password: String)
}
