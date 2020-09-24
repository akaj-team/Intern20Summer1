package com.asiantech.intern20summer1.w12.ui.login

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.source.repository.LoginRepository
import com.asiantech.intern20summer1.w12.extension.isValidEmail
import com.asiantech.intern20summer1.w12.extension.isValidPassword
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response


class LoginViewModel(private val repository: LoginRepository) : ViewModel(), LoginVMContact {

    private var isValidInfo = BehaviorSubject.create<Boolean>()

    override fun login(email: String, password: String): Single<Response<User>>? =
        repository.login(email, password)

    override fun isValidInformationStatus(): BehaviorSubject<Boolean> = isValidInfo

    override fun isValidInformation(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            isValidInfo.onNext(true)
        } else {
            isValidInfo.onNext(false)
        }
    }
}
