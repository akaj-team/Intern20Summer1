package com.asiantech.intern20summer1.viewmodel

import com.asiantech.intern20summer1.BaseTest
import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import com.asiantech.intern20summer1.w11.data.source.datasource.LoginDataSource
import com.asiantech.intern20summer1.w11.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.w11.ui.fragment.login.LoginVM
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 25/09/2020.
 * This is LoginViewModelTest TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class LoginViewModelTest : BaseTest() {

    private lateinit var viewModel: LoginVM

    @Mock
    private lateinit var localRepository: LocalDataSource

    @Mock
    private lateinit var loginRepository: LoginDataSource


    override fun initTest() {
        viewModel = LoginVM(loginRepository, localRepository)
    }

    @Test
    fun `Given email and password - When call AutoSignIn - Then AutoSignIn success`() {
        /* Given */
        val token = "token"
        val testObserver = TestObserver<Response<Account>>()
        val account = Account(0, "email", "name", "token")

        /* When */
        Mockito.`when`(loginRepository.autoSignIn(token)).thenReturn(
            Single.just(Response.success(account))
        )
        viewModel.autoSignIn(token)?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.token, CoreMatchers.`is`("token"))
            true
        }

    }

    @Test
    fun `Given email and password - When call login - Then login success`() {
        /* Given */
        val email = "huypro@gmail.com"
        val password = "huy123456"
        val testObserver = TestObserver<Response<Account>>()
        val account = Account(0, "email", "name", "token")

        /* When */
        Mockito.`when`(loginRepository.login(email, password)).thenReturn(
            Single.just(Response.success(account))
        )

        viewModel.login(email, password)?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.token, CoreMatchers.`is`("token"))
            true
        }
    }

    @Test
    fun `Given email,password,fullname - When call createUser - Then createUser success`() {
        /* Given */
        val requestAccount = RequestAccount("email", "password", "name")
        val testObserver = TestObserver<Response<Account>>()
        val account = Account(0, "email", "name", "token")

        /* When */
        Mockito.`when`(loginRepository.createUser(requestAccount)).thenReturn(
            Single.just(Response.success(account))
        )

        viewModel.createUser(requestAccount)?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.token, CoreMatchers.`is`("token"))
            true
        }
    }
}
