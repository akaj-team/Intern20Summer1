package com.asiantech.intern20summer1.w11.ui.fragment.home

import android.net.Uri
import com.asiantech.intern20summer1.w11.data.models.PostContent
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import com.asiantech.intern20summer1.w11.data.source.datasource.HomeDataSource
import com.asiantech.intern20summer1.w11.data.source.datasource.LocalDataSource
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is HomeViewModel
 */

class HomeVM(
    private val homeRepository: HomeDataSource,
    private val localRepository: LocalDataSource
) : HomeVMContract {

    companion object {
        private const val RECYCLER_LOAD_SIZE = 10
        private const val TYPE_TEXT = "text"
    }

    private var progressBehavior = BehaviorSubject.create<Boolean>()
    private var postLists = mutableListOf<PostItem>()
    private var postListRecycler = mutableListOf<PostItem>()

    fun getPost(position: Int): PostItem? {
        return if (position < postLists.size) {
            postLists[position]
        } else {
            null
        }
    }

    override fun likePost(position: Int): Single<Response<ResponseLike>>? {
        val token = localRepository.getToken()
        return homeRepository
            .likePost(token, postLists[position].id)
            ?.doOnSuccess { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        postLists[position].like_flag = it.like_flag
                        postLists[position].like_count = it.like_count
                    }
                }
            }
    }

    override fun getDataAdapter(): MutableList<PostItem> = postListRecycler
    override fun getPostsData(): Single<Response<List<PostItem>>>? {
        val token = localRepository.getToken()
        return homeRepository
            .getPosts(token)
            ?.doOnSubscribe { progressBehavior.onNext(true) }
            ?.doOnSuccess { response ->
                postLists.clear()
                postListRecycler.clear()
                response.body()?.toCollection(postLists)
                postListRecycler.add(PostItem())
                if (postLists.size < RECYCLER_LOAD_SIZE) {
                    initDataRecycler(0, postLists.size)
                } else {
                    initDataRecycler(0, RECYCLER_LOAD_SIZE)
                }
            }
            ?.doFinally { progressBehavior.onNext(false)
                postLists.forEach {
                    print("${it.id}, ")
                }}
    }
    override fun searchData(key: String): Single<Response<List<PostItem>>>? =
        homeRepository
            .getPosts(localRepository.getToken())
            ?.doOnSuccess {
                if (it.isSuccessful) {
                    postListRecycler.clear()
                    postLists.clear()
                    it.body()?.forEach { post ->
                        if (post.content.contains(key,true)) {
                            postLists.add(post)
                        }
                    }
                    postListRecycler.add(PostItem())
                    if (postLists.size < RECYCLER_LOAD_SIZE) {
                        initDataRecycler(0, postLists.size)
                    } else {
                        initDataRecycler(0, RECYCLER_LOAD_SIZE)
                    }
                }
            }

    override fun loadMoreData() {
        val currentSize = postListRecycler.size - 1
        if (postLists.size - currentSize < RECYCLER_LOAD_SIZE) {
            initDataRecycler(currentSize, postLists.size)
        } else {
            initDataRecycler(currentSize, currentSize + RECYCLER_LOAD_SIZE)
        }
    }

    override fun getIdUser(): Int? = localRepository.getIdUser()
    override fun createPost(
        content: String,
        uri: Uri?
    ): Single<Response<ResponsePost>>? {
        val token = localRepository.getToken()
        val postJson = Gson().toJson(PostContent(content)).toString()
        val body = postJson.toRequestBody(TYPE_TEXT.toMediaTypeOrNull())
        val imageFile = localRepository.createMultiPartBody(uri)
        return homeRepository
            .createPost(token, imageFile, body)
    }

    override fun updatePost(
        idPost: Int,
        content: String,
        uri: Uri?
    ): Single<Response<ResponsePost>>? {
        val token = localRepository.getToken()
        val postJson = Gson().toJson(PostContent(content)).toString()
        val body = postJson.toRequestBody(TYPE_TEXT.toMediaTypeOrNull())
        val imageFile = localRepository.createMultiPartBody(uri)
        println(token)
        println(body)
        println(imageFile)
        println(idPost)
        return homeRepository
            .updatePost(token, idPost, imageFile, body)
    }

    override fun progressDialogStatus(): BehaviorSubject<Boolean>? = progressBehavior


    override fun initDataRecycler(start: Int, end: Int) {
        postListRecycler.removeAt(postListRecycler.size - 1)
        for (i in start until end) {
            postListRecycler.add(postLists[i])
        }
        postListRecycler.add(PostItem())
    }
}
