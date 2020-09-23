package com.asiantech.intern20summer1.w12.data.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {

    private var retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 60
    private const val BASE_URL = "https://at-a-trainning.000webhostapp.com"

    internal fun createUserService(): UserAPI? = createServiceClient()?.create(UserAPI::class.java)

    internal fun createPostService(): PostAPI? = createServiceClient()?.create(PostAPI::class.java)

//    internal fun createApiService() : APIService? = createServiceClient()?.create(APIService::class.java)

    private fun createServiceClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
