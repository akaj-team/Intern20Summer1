package com.asiantech.intern20summer1.w11.data.source.remote.network

import com.asiantech.intern20summer1.w11.data.models.*
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

interface ApiService {

    companion object {
        private const val PART_CREATE_USER = "/api/user"
        private const val PART_AUTO_SIGN_IN = "/api/autosignin"
        private const val PART_LOGIN = "/api/login"
        const val PART_CREATE_POST = "/api/post"
        const val PART_UPDATE_POST = "/api/post/{id}"
        const val PART_LIKE_POST = "/api/post/{id}/like"
        const val PAST_GET_POSTS = "/api/posts"
    }

    @GET(PART_AUTO_SIGN_IN)
    fun autoSignIn(@Header("token") token: String): Single<Response<Account>>

    @POST(PART_LOGIN)
    @FormUrlEncoded
    fun login(
        @Field("email") email: String = "",
        @Field("password") password: String = ""
    ): Single<Response<Account>>

    @POST(PART_CREATE_USER)
    fun createUser(@Body request: RequestAccount): Single<Response<Account>>

    @Multipart
    @POST(PART_CREATE_POST)
    fun createPost(
        @Header("token") token: String,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: RequestBody
    ): Single<Response<ResponsePost>>

    @Multipart
    @POST(PART_UPDATE_POST)
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0,
        @Part image: MultipartBody.Part? = null,
        @Part("body") body: RequestBody
    ): Single<Response<ResponsePost>>

    @POST(PART_LIKE_POST)
    fun likePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Single<Response<ResponseLike>>

    @GET(PAST_GET_POSTS)
    fun getPostLists(@Header("token") token: String): Single<Response<List<PostItem>>>
}
