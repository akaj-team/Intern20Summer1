package com.asiantech.intern20summer1.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientAPI {

    private var retrofit: Retrofit? = null

    //  private const val API_URL = "https://at-a-trainning.000webhostapp.com/"
    private const val API_URL = "https://5f4e0faceeec51001608f40b.mockapi.io/"

    fun createServiceClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}
