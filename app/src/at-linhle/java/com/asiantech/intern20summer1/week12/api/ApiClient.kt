package com.asiantech.intern20summer1.week12.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val baseURL = "https://at-a-trainning.000webhostapp.com"
    private var retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 60

    internal fun createUserService(): ApiUserService? =
        getClient()?.create(ApiUserService::class.java)

    internal fun cretePostService(): ApiPostService? =
        getClient()?.create(ApiPostService::class.java)

    private fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient().newBuilder()
                        .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .build()
                )
                .build()
        }
        return retrofit
    }
}
