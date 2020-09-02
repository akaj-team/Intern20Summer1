package com.asiantech.intern20summer1.w10.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    companion object {
        private const val BASE_URL = "https://at-a-trainning.000webhostapp.com/"
    }

    private var retrofit: Retrofit? = null
    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .build()

//            .connectTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)

    fun newInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
        }
        return retrofit
    }
}