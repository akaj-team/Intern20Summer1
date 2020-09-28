package com.asiantech.intern20summer1.week12.ui.register

import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import retrofit2.Response

interface RegisterMVContract {
    fun register(userRegister: UserRegister): Single<Response<User>>?

    fun infoValidateStatus(): PublishSubject<Boolean>

    fun validateRegisterInformation(fullName: String, email: String, password: String)
}
