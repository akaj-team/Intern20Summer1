package com.asiantech.intern20summer1.w12.ui.register

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import com.asiantech.intern20summer1.w12.data.source.repository.RegisterRepository
import com.asiantech.intern20summer1.w12.extension.isValidEmail
import com.asiantech.intern20summer1.w12.extension.isValidPassword
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel(),
    RegisterVMContact {

    private val isValidRegisterStatus = BehaviorSubject.create<Boolean>()

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        repository.register(userRegister)

    override fun isValidInfoStatus(): BehaviorSubject<Boolean> = isValidRegisterStatus

    override fun isValidInformation(email: String, fullName: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password) && fullName.isNullOrEmpty()) {
            isValidRegisterStatus.onNext(true)
        } else {
            isValidRegisterStatus.onNext(false)
        }
    }
}
