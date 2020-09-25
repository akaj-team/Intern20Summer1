package com.asiantech.intern20summer1.week12.ui.home

import com.asiantech.intern20summer1.week12.data.models.ApiResponse
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import okhttp3.MultipartBody
import retrofit2.Response

interface PostVMContract {
    fun getAllPost(): MutableList<NewPost>

    fun getListPostAdapter(): MutableList<NewPost?>

    fun getListPost(): MutableList<NewPost?>

    fun getListPostFromServer(token: String): Single<Response<MutableList<NewPost>>>?

    fun updateLikePost(token: String, id: Int): Single<Response<ResponseLike>>?

    fun refreshData()

    fun loadMore(positionItem: Int)

    fun updateProgressBar(): BehaviorSubject<Boolean>

    fun searchPostFromServer(key : String): Single<Response<MutableList<NewPost>>>?

    fun getResultSearch(): Boolean
    fun getIdUser(): Int?
    fun getToken(): String?
}
