package com.asiantech.intern20summer1.w11.api

import com.asiantech.intern20summer1.w11.models.PostItem
import io.reactivex.Observable

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 14/09/2020.
 * This is ApiRepos TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class ApiRepos(private val apiHelper: ApiHelper) {

    fun getPosts(token: String): Observable<List<PostItem>> {
        return apiHelper.getPosts(token)
    }
}
     