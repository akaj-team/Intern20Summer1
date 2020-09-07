package com.asiantech.intern20summer1.api.w10

import com.asiantech.intern20summer1.api.w10.ClientAPI.CREATE_POST
import com.asiantech.intern20summer1.api.w10.ClientAPI.DELETE_POST
import com.asiantech.intern20summer1.api.w10.ClientAPI.GET_API_POSTS
import com.asiantech.intern20summer1.api.w10.ClientAPI.LIKE_POST
import com.asiantech.intern20summer1.api.w10.ClientAPI.UPDATE_POST
import com.asiantech.intern20summer1.model.w10.ApiResponse
import com.asiantech.intern20summer1.model.w10.NewPost
import com.asiantech.intern20summer1.model.w10.Post
import com.asiantech.intern20summer1.model.w10.ResponseLike
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostsAPI {
    @GET(GET_API_POSTS)
    fun getPost(@Header("token") token: String): Call<MutableList<NewPost>>

    @DELETE(DELETE_POST)
    fun deletePosts(@Header("token") token: String, @Path("id") id: Int): Call<ApiResponse>

    @Multipart
    @POST(CREATE_POST)
    fun createPost(
        @Header("token") token: String,
        @Part("body") body: Post,
        @Part image: MultipartBody.Part? = null
    ): Call<ApiResponse>

    @Multipart
    @POST(UPDATE_POST)
    fun updatePost(
        @Header("token") token: String,
        @Path("id") id: Int,
        @Part("body") body: Post,
        @Part image: MultipartBody.Part? = null
    ): Call<ApiResponse>

    @POST(LIKE_POST)
    fun likePost(@Header("token") token: String, @Path("id") id: Int = 0): Call<ResponseLike>
}
