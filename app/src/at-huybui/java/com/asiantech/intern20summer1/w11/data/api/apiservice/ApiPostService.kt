package com.asiantech.intern20summer1.w11.data.api.apiservice

import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 02/09/2020.
 * This is ApiPostService class. It will progress Api for Post model
 */

interface ApiPostService {

    companion object {
        const val PART_CREATE_POST = "/api/post"
        const val PART_UPDATE_POST = "/api/post/{id}"
        const val PART_LIKE_POST = "/api/post/{id}/like"
        const val PAST_GET_POSTS = "/api/posts"
    }

    @Multipart
    @POST(PART_CREATE_POST)
    fun createPost(
        @Header("token") token: String,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: RequestBody
    ): Observable<Response<ResponsePost>>

    @Multipart
    @POST(PART_UPDATE_POST)
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: RequestBody
    ): Single<Response<ResponsePost>>

    @DELETE(PART_UPDATE_POST)
    fun deletePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Observable<Response<ResponsePost>>

    @POST(PART_LIKE_POST)
    fun likePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Observable<Response<ResponseLike>>

    @GET(PAST_GET_POSTS)
    fun getPostLists(@Header("token") token: String): Observable<Response<List<PostItem>>>
}
