package com.asiantech.intern20summer1.w10.api

import com.asiantech.intern20summer1.w10.models.PostItem
import com.asiantech.intern20summer1.w10.models.ResponseLike
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiPostService {

    companion object {
        private const val PART_CREATE_POST = "/api/post"
        private const val PART_UPDATE_POST = "/api/post/{id}"
        private const val PART_LIKE_POST = "/api/post/{id}/like"
        private const val PAST_GET_POST_LIST ="/api/posts"
    }


    @Multipart
    @POST(PART_CREATE_POST)
    fun createPost(
        @Part("file\"; filename=\"pp.png\" ") file: RequestBody?,
        @Part("FirstName") fname: RequestBody?
    )

    @Multipart
    @POST(PART_UPDATE_POST)
    fun updatePost(
        @Part("id") id: Int = 0,
        @Part("file\"; filename=\"pp.png\" ") file: RequestBody?,
        @Part("FirstName") fname: RequestBody?
    )

    @DELETE(PART_UPDATE_POST)
    fun deletePost(@Part("id") id: Int = 0)

    @POST(PART_LIKE_POST)
    fun likePost(@Part("id") id: Int = 0): Call<ResponseLike>

    @GET(PAST_GET_POST_LIST)
    fun getPostLists(@Header("token") token: String): Call<List<PostItem>>
}
