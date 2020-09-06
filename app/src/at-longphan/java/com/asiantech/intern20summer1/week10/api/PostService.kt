package com.asiantech.intern20summer1.week10.api

import com.asiantech.intern20summer1.week10.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PostService {

    @GET("api/posts")
    fun getPosts(@Header("token") token: String?): Call<List<Post>>

    /*@POST("api/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): Call<User>*/

    /*@POST("api/login")
    fun login(@Body userLogin: UserLogin): Call<User>*/
}
