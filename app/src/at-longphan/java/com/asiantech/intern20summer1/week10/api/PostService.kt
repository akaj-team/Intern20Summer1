package com.asiantech.intern20summer1.week10.api

import com.asiantech.intern20summer1.week10.model.Post
import com.asiantech.intern20summer1.week10.model.ToggleLikeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {

    @GET("api/posts")
    fun getPosts(@Header("token") token: String?): Call<List<Post>>

    @POST("api/post/{id}/like")
    fun toggleLike(@Header("token") token: String?, @Path("id") postId: Int?): Call<ToggleLikeResponse>

    /*@POST("api/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): Call<User>*/

    /*@POST("api/login")
    fun login(@Body userLogin: UserLogin): Call<User>*/
}
