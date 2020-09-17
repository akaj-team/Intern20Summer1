package com.asiantech.intern20summer1.w11.data.api

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 04/09/2020.
 * This is ApiError class. it progress message return from api server
 */

data class ApiError(var statusCode: Int = 0, var message: String = "")

class ErrorUtils {

    fun parseError(response: Response<*>): ApiError? {
        val converter: Converter<ResponseBody, ApiError>? = ApiClient.getClientInstance()
            ?.responseBodyConverter(ApiError::class.java, arrayOfNulls<Annotation>(0))
        val error: ApiError?
        error = try {
            response.errorBody()?.let { converter?.convert(it) }
        } catch (e: IOException) {
            return ApiError()
        }
        error?.statusCode = response.code()
        return error
    }
}
