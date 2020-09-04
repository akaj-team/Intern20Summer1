package com.asiantech.intern20summer1.api

import com.asiantech.intern20summer1.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientAPI {

    private var retrofit: Retrofit? = null
    private const val API_TIMEOUT = 60

    private const val API_URL = "https://at-a-trainning.000webhostapp.com/"
    internal fun createUserService(): UserClient? =
        getClient()?.create(UserClient::class.java)

    internal fun createPost(): PostsAPI? =
        getClient()?.create(PostsAPI::class.java)

    //private const val API_URL = "https://5f4e0faceeec51001608f40b.mockapi.io/"
    private fun getClient(): Retrofit? {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
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
