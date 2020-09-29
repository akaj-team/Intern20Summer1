package com.asiantech.intern20summer1.viewmodel

import com.asiantech.intern20summer1.BaseTest
import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import com.asiantech.intern20summer1.week12.ui.login.LoginViewModel
import com.asiantech.intern20summer1.week12.ui.register.RegisterViewModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

/**
 * Asian tech Co., ltd.
 * Intern20Summer1 Project
 * Create by Vuong Phan T. on 9/25/20.
 * This is RegisterViewModelTest TODO("Not yes implemented")
 */
class RegisterViewModelTest : BaseTest() {
    private lateinit var viewModel: RegisterViewModel

    @Mock
    private lateinit var registerRepository: UserDataSource
    override fun initTest() {
        viewModel = RegisterViewModel(registerRepository)
    }

    @Test
    fun `Given add new User - When call addUser - Then addUser success`() {
        val email = "a@gmail.com"
        val password = "123"
        val fullName = "vuong"
        val user = UserRegister(email, password, fullName)
        val testObserver = TestObserver<Response<UserAutoSignIn>>()
        val account = UserAutoSignIn(0, email, fullName, "token")
        Mockito.`when`(registerRepository.addUser(user))
            .thenReturn(Single.just(Response.success(account)))
        viewModel.addUserRegister(user)?.subscribe(testObserver)
        testObserver.assertValue {
            Assert.assertThat(it.body()?.token, CoreMatchers.`is`("token"))
            true
        }
    }
}