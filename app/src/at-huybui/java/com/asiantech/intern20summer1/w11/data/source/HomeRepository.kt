package com.asiantech.intern20summer1.w11.data.source

import android.content.Context
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import com.asiantech.intern20summer1.w11.data.source.datasource.HomeDataSource
import com.asiantech.intern20summer1.w11.data.source.remote.HomeRemoteDataSource
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is HomeRepository
 */
class HomeRepository(private val context: Context) : HomeDataSource {

    private val postRemote = HomeRemoteDataSource()

    override fun getPosts(token: String): Single<Response<List<PostItem>>>? =
        postRemote.getPosts(token)

    override fun createPost(
        token: String,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Single<Response<ResponsePost>>? =
        postRemote.createPost(token, image, body)

    override fun updatePost(
        token: String,
        id: Int,
        image:MultipartBody.Part?,
        body: RequestBody
    ): Single<Response<ResponsePost>>? =
        postRemote.updatePost(token, id, image, body)

    override fun likePost(token: String, id: Int): Single<Response<ResponseLike>>? =
        postRemote.likePost(token, id)
}
