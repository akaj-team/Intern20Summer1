package com.asiantech.intern20summer1.datasource

import com.asiantech.intern20summer1.BaseTest
import com.asiantech.intern20summer1.week12.data.source.remote.UserRemoteDataSource
import com.asiantech.intern20summer1.week12.data.source.remote.network.UserClient
import org.mockito.Mock

/**
 * Asian tech Co., ltd.
 * Intern20Summer1 Project
 * Create by Vuong Phan T. on 9/25/20.
 * This is LoginRemoteDataSourceTest TODO("Not yes implemented")
 */
class LoginRemoteDataSourceTest : BaseTest() {
    private lateinit var loginRemoteDataSource: UserRemoteDataSource
    override fun initTest() {
        loginRemoteDataSource = UserRemoteDataSource()
    }
}