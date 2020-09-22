package com.asiantech.intern20summer1.week12.data.source.remote.network

import com.asiantech.intern20summer1.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientAPI {

    private var retrofit: Retrofit? = null
    private const val API_TIMEOUT = 60
    internal const val MESSAGE_LOGIN_INCORRECT = "Email or password incorrect!"
    internal const val GET_API_POSTS = "api/posts"
    internal const val DELETE_POST = "api/post/{id}"
    internal const val CREATE_POST = "api/post"
    internal const val UPDATE_POST = "api/post/{id}"
    internal const val LIKE_POST = "api/post/{id}/like"

    private const val API_URL = "https://at-a-trainning.000webhostapp.com/"
    internal fun createUserService(): UserClient? =
        getClient()?.create(UserClient::class.java)

    internal fun createPost(): PostsAPI? =
        getClient()?.create(PostsAPI::class.java)

    internal fun getClient(): Retrofit? {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(
                    OkHttpClient().newBuilder()
                        .connectTimeout(API_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .readTimeout(API_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .writeTimeout(API_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .addInterceptor(httpLoggingInterceptor)
                        .build()
                )
                .build()
        }
        return retrofit
    }
}
