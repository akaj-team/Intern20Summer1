package com.asiantech.intern20summer1.week12.api

import com.asiantech.intern20summer1.week12.models.LikeResponse
import com.asiantech.intern20summer1.week12.models.Post
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiPostService {

    @GET("/api/posts")
    fun getListPost(@Header("token") token: String): Single<Response<MutableList<Post>>>

    @POST("/api/post/{id}/like")
    fun updatePostLike(
        @Header("token") token: String,
        @Path("id") id: Int
    ): Single<LikeResponse>
}
