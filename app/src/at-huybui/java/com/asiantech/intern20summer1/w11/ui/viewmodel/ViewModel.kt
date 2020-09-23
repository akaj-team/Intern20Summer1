package com.asiantech.intern20summer1.w11.ui.viewmodel

import com.asiantech.intern20summer1.w11.data.models.*
import com.asiantech.intern20summer1.w11.data.source.LocalRepository
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is HomeViewModel
 */

class ViewModel(
//    private val remoteDataSource: RemoteRepository? = null,
    private val localDataSource: LocalRepository? = null
) : ViewModelContract {

    override fun getPosts(token: String): Observable<Response<List<PostItem>>>? =
        remoteDataSource?.getPosts(token)

    override fun createPost(
        token: String,
        image: String?,
        body: RequestBody
    ): Observable<Response<ResponsePost>>? =
        remoteDataSource?.createPost(token, image, body)

    override fun updatePost(
        token: String,
        id: Int,
        image: String?,
        body: RequestBody
    ): Single<Response<ResponsePost>>? =
        remoteDataSource?.updatePost(token, id, image, body)

    override fun likePost(token: String, id: Int): Observable<Response<ResponseLike>>? =
        remoteDataSource?.likePost(token, id)


    override fun autoSignIn(token: String): Observable<Response<Account>>? =
        remoteDataSource?.autoSignIn(token)

    override fun login(email: String, password: String): Observable<Response<Account>>? =
        remoteDataSource?.login(email, password)

    override fun createUser(request: RequestAccount): Observable<Response<Account>>? =
        remoteDataSource?.createUser(request)

    override fun putToken(token: String) {
        localDataSource?.putToken(token)
    }

    override fun getToken(): String? = localDataSource?.getToken()

    override fun putIdUser(idUser: Int) {
        localDataSource?.putIdUser(idUser)
    }

    override fun getIdUser(): Int? = localDataSource?.getIdUser()

    override fun putIsLogin(isLogin: Boolean) {
        localDataSource?.putIsLogin(isLogin)
    }

    override fun getIsLogin(): Boolean? = localDataSource?.getIsLogin()
}
