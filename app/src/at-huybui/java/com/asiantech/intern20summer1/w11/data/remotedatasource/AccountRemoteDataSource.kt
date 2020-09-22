package com.asiantech.intern20summer1.w11.data.remotedatasource

import com.asiantech.intern20summer1.w11.data.api.ApiClient
import com.asiantech.intern20summer1.w11.data.models.Account
import com.asiantech.intern20summer1.w11.data.models.RequestAccount
import io.reactivex.Observable
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is AccountRemoteDataSource
 */
class AccountRemoteDataSource : AccountDataSource {
    private val callRx = ApiClient.getApiAccount()
    override fun autoSignIn(token: String): Observable<Response<Account>>? =
        callRx?.autoSignIn(token)

    override fun login(email: String, password: String): Observable<Response<Account>>? =
        callRx?.login(email, password)

    override fun createUser(request: RequestAccount): Observable<Response<Account>>? =
        callRx?.createUser(request)
}
     