package com.asiantech.intern20summer1.week12.ui.login

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.data.model.User
import com.asiantech.intern20summer1.week12.data.source.LoginRepository
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response
import java.util.regex.Pattern

class LoginViewModel(private val repository: LoginRepository) : ViewModel(), LoginMVContract {

    companion object {
        internal const val MAX_EMAIL_LENGTH = 264
    }

    private val validateLoginInformationStatus = BehaviorSubject.create<Boolean>()
    private val passwordPattern = Pattern.compile("""^(?=.*).{8,16}$""")

    override fun login(email: String, password: String): Single<Response<User>>? =
        repository.login(email, password)

    override fun infoValidateStatus(): BehaviorSubject<Boolean> = validateLoginInformationStatus

    override fun validateLoginInformation(email: String, password: String) {
        if (passwordPattern.matcher(password).matches()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && email.length <= MAX_EMAIL_LENGTH
        ) {
            validateLoginInformationStatus.onNext(true)
        } else {
            validateLoginInformationStatus.onNext(false)
        }
    }
}
