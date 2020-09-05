package com.asiantech.intern20summer1.api

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException


data class ApiError(var statusCode: Int = 0, var message: String = "")

class ErrorUtils {

    fun parseError(response: Response<*>): ApiError? {
        val converter: Converter<ResponseBody, ApiError>? = ClientAPI.getClient()
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
