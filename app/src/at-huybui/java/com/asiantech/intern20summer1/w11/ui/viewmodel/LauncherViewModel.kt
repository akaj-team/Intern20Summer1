package com.asiantech.intern20summer1.w11.ui.viewmodel

import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import com.asiantech.intern20summer1.w11.data.remotedatasource.AccountDataSource
import io.reactivex.Observable
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is LauncherViewModel TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class LauncherViewModel(private val accountDataSource: AccountDataSource) :
    LauncherViewModelContract {
    override fun autoSignIn(token: String): Observable<Response<Account>>? =
        accountDataSource.autoSignIn(token)

    override fun login(email: String, password: String): Observable<Response<Account>>? =
        accountDataSource.login(email, password)

    override fun createUser(request: RequestAccount): Observable<Response<Account>>? =
        accountDataSource.createUser(request)
}
     