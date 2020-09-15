package com.asiantech.intern20summer1.w11.api

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 14/09/2020.
 * This is ApiHelper TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class ApiHelper(private val apiService: ApiPostService) {

    fun getPosts(token: String) = apiService.getPostLists(token)

}
     