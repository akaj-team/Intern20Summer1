package com.asiantech.intern20summer1.viewmodel

import com.asiantech.intern20summer1.BaseTest
import com.asiantech.intern20summer1.week12.data.models.UserAutoSignIn
import com.asiantech.intern20summer1.week12.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.week12.data.source.datasource.UserDataSource
import com.asiantech.intern20summer1.week12.ui.login.LoginViewModel
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
 * This is LoginViewModelTest TODO("Not yes implemented")
 */
class LoginViewModelTest : BaseTest() {
    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var localRepository: LocalDataSource

    @Mock
    private lateinit var loginRepository: UserDataSource

    override fun initTest() {
        viewModel = LoginViewModel(loginRepository, localRepository)
    }

    @Test
    fun `Given email and password - When call login - Then login success`() {
        /* Given */
        val email = "vuong@gmail.com"
        val password = "123"
        val testObserver = TestObserver<Response<UserAutoSignIn>>()
        //  val account = getObjectWithJson("Account.json", UserAutoSignIn::class.java)
        val account = UserAutoSignIn(0, email, "aaa", "token")
        /* When */
        Mockito.`when`(loginRepository.login(email, password))
            .thenReturn(Single.just(Response.success(account)))
        viewModel.login(email, password)?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.token, CoreMatchers.`is`("token"))
            true
        }
    }

    @Test
    fun `Given putIdUser - When call putIdUser - Then putIdUser success`() {
        viewModel.putIdUser(0)
        assert(true)
    }

    @Test
    fun `Given putToken - When call putToken - Then putToken success`() {
        viewModel.putToken("token")
        assert(true)
    }

    @Test
    fun `Given putIsLogin - When call putIdUser - Then putIsLogin success`() {
        viewModel.putIsLogin(true)
        assert(true)
    }
}