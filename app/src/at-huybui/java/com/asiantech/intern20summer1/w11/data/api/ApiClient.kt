package com.asiantech.intern20summer1.w11.data.api

import com.asiantech.intern20summer1.w11.data.api.apiservice.ApiAccountService
import com.asiantech.intern20summer1.w11.data.api.apiservice.ApiPostService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 02/09/2020.
 * This is Api class. It is class progress connect Api
 */

class ApiClient {

    companion object {
        //-- message from api server
        internal const val MESSAGE_CREATE_POST_SUCCESS = "Create Post success."
        internal const val MESSAGE_LOGIN_INCORRECT = "Email or password incorrect!"
        internal const val MESSAGE_UNAUTHORIZED = "Unauthorized!"
        internal const val MESSAGE_EMAIL_HAS_BEEN_TAKEN = "The email has already been taken."
        internal const val MESSAGE_UPDATE_POST_SUCCESS = "Update post success!"

        //-- base url and image url
        private const val BASE_URL = "https://at-a-trainning.000webhostapp.com"
        internal const val IMAGE_URL = "https://at-a-trainning.000webhostapp.com/images/"

        //-- create retrofit
        private var retrofit: Retrofit? = null
        private fun provideOkHttpClient(): OkHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        internal fun getClientInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build()
            }
            return retrofit
        }

        internal fun getApiPosts(): ApiPostService? =
            getClientInstance()?.create(ApiPostService::class.java)

        internal fun getApiAccount(): ApiAccountService? =
            getClientInstance()?.create(ApiAccountService::class.java)
    }
}
