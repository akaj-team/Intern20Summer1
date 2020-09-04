package com.asiantech.intern20summer1.w10.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {

    companion object {
        //-- message from api server
        internal const val MESSAGE_CREATE_POST_SUCCESS = "Create Post success."
        internal const val MESSAGE_MISSING_DATA = "Missing data!"
        internal const val MESSAGE_LOGIN_INCORRECT = "Email or password incorrect!"
        internal const val MESSAGE_UNAUTHORIZED = "Unauthorized!"
        internal const val MESSAGE_EMAIL_HAS_BEEN_TAKEN = "The email has already been taken."

        //--
        private const val BASE_URL = "https://at-a-trainning.000webhostapp.com"
        private var retrofit: Retrofit? = null
        private fun provideOkHttpClient(): OkHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        internal fun getInstance(): Retrofit? {
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
}