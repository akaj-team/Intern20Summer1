package com.asiantech.intern20summer1.week12.data.source.remote.network

import com.asiantech.intern20summer1.week12.data.models.ApiResponse
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI.CREATE_POST
import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI.DELETE_POST
import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI.GET_API_POSTS
import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI.LIKE_POST
import com.asiantech.intern20summer1.week12.data.source.remote.network.ClientAPI.UPDATE_POST
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PostsAPI {
    @GET(GET_API_POSTS)
    fun getPost(@Header("token") token: String): Single<Response<MutableList<NewPost>>>

    @DELETE(DELETE_POST)
    fun deletePosts(@Header("token") token: String, @Path("id") id: Int): Single<ApiResponse>

    @Multipart
    @POST(CREATE_POST)
    fun createPost(
        @Header("token") token: String,
        @Part("body") body: Post,
        @Part image: MultipartBody.Part? = null
    ): Single<Response<ApiResponse>>

    @Multipart
    @POST(UPDATE_POST)
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int,
        @Part("body") body: Post,
        @Part image: MultipartBody.Part? = null
    ): Single<ApiResponse>

    @POST(LIKE_POST)
    fun likePost(
        @Header("token") token: String,
        @Path("id") id: Int = 0
    ): Single<Response<ResponseLike>>
}
