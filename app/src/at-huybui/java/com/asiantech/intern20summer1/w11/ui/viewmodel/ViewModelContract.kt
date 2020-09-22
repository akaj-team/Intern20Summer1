package com.asiantech.intern20summer1.w11.ui.viewmodel

import com.asiantech.intern20summer1.w11.data.models.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is HomeViewModelContract
 */
interface ViewModelContract {
    fun getPosts(token: String): Observable<Response<List<PostItem>>>?

    fun createPost(
        token: String,
        image: String? = null,
        body: RequestBody
    ): Observable<Response<ResponsePost>>?

    fun updatePost(
        token: String,
        id: Int = 0,
        image: String?,
        body: RequestBody
    ): Single<Response<ResponsePost>>?

    fun likePost(token: String, id: Int): Observable<Response<ResponseLike>>?

    fun autoSignIn(token: String): Observable<Response<Account>>?

    fun login(
        email: String = "",
        password: String = ""
    ): Observable<Response<Account>>?

    fun createUser(request: RequestAccount): Observable<Response<Account>>?

    fun putToken(token: String)

    fun getToken(): String?

    fun putIdUser(idUser: Int)

    fun getIdUser(): Int?

    fun putIsLogin(isLogin: Boolean)

    fun getIsLogin(): Boolean?
}
     