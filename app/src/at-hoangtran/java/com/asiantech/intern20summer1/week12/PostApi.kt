package com.asiantech.intern20summer1.week12

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {

    @GET("/api/posts")
    fun getListPost(@Header("token") token: String): Single<MutableList<Post>>

    @POST("/api/post/{id}/like")
    fun updatePostLike(
        @Header("token") token: String,
        @Path("id") id: Int
    ): Single<Response<LikeResponse>>
}
