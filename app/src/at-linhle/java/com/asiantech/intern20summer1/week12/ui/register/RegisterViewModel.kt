package com.asiantech.intern20summer1.week12.ui.register

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.model.UserRegister
import com.asiantech.intern20summer1.week12.data.source.LoginRepository
import com.asiantech.intern20summer1.week12.ui.login.LoginViewModel
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response
import java.util.regex.Pattern

class RegisterViewModel(private val repository: LoginRepository) : ViewModel(), RegisterMVContract {

    companion object{
        private const val MAX_FULL_NAME_LENGTH = 64
    }

    private val validateRegisterInformationStatus = BehaviorSubject.create<Boolean>()
    private val passwordPattern = Pattern.compile("""^(?=.*).{8,16}$""")

    override fun register(userRegister: UserRegister): Single<Response<User>>? =
        repository.register(userRegister)

    override fun infoValidateStatus(): BehaviorSubject<Boolean> = validateRegisterInformationStatus

    override fun validateRegisterInformation(fullName: String, email: String, password: String) {
        if (fullName.length <= MAX_FULL_NAME_LENGTH
            &&passwordPattern.matcher(password).matches()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && email.length <= LoginViewModel.MAX_EMAIL_LENGTH
        ) {
            validateRegisterInformationStatus.onNext(true)
        } else {
            validateRegisterInformationStatus.onNext(false)
        }
    }
}
