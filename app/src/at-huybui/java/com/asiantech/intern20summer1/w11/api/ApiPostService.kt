package com.asiantech.intern20summer1.w11.api

import com.asiantech.intern20summer1.w11.models.PostItem
import com.asiantech.intern20summer1.w11.models.ResponseLike
import com.asiantech.intern20summer1.w11.models.ResponsePost
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    ): Observable<ResponsePost>

    @Multipart
    @POST(PART_UPDATE_POST)
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: RequestBody
    ): Observable<ResponsePost>

    @DELETE(PART_UPDATE_POST)
    fun deletePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Observable<ResponsePost>

    @POST(PART_LIKE_POST)
    fun likePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Observable<ResponseLike>

    @GET(PAST_GET_POSTS)
    fun getPostLists(@Header("token") token: String): Observable<List<PostItem>>
}
