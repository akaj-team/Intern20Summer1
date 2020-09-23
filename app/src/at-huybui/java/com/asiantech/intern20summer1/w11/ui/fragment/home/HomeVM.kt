package com.asiantech.intern20summer1.w11.ui.fragment.home

import android.net.Uri
import com.asiantech.intern20summer1.w11.data.models.PostContent
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import com.asiantech.intern20summer1.w11.data.source.HomeRepository
import com.asiantech.intern20summer1.w11.data.source.LocalRepository
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
    private val homeRepository: HomeRepository?,
    private val localRepository: LocalRepository
) : HomeVMContract {

    companion object {
        private const val RECYCLER_LOAD_SIZE = 10
        private const val TYPE_TEXT = "text"
    }

    var progressBehavior = BehaviorSubject.create<Boolean>()
    var postLists = mutableListOf<PostItem>()
    var postListRecycler = mutableListOf<PostItem>()

    override fun likePost(position: Int): Single<Response<ResponseLike>>? =
        homeRepository
            ?.likePost(localRepository.getToken(), postLists[position].id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSuccess { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        postLists[position].like_flag = it.like_flag
                        postLists[position].like_count = it.like_count
                    }
                }
            }

    override fun getDataAdapter(): MutableList<PostItem> = postListRecycler
    override fun getPostsData(): Single<Response<List<PostItem>>>? =
        homeRepository
            ?.getPosts(localRepository.getToken())
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
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
            ?.doFinally { progressBehavior.onNext(false) }

    override fun searchData(key: String): Single<Response<List<PostItem>>>? =
        homeRepository
            ?.getPosts(localRepository.getToken())
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
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
        val imagePart = homeRepository?.createMultiPartBody(uri)
        return homeRepository
            ?.createPost(token, imagePart, body)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun updatePost(
        idPost: Int,
        content: String,
        uri: Uri?
    ): Single<Response<ResponsePost>>? {
        val token = localRepository.getToken()
        val postJson = Gson().toJson(PostContent(content)).toString()
        val body = postJson.toRequestBody(TYPE_TEXT.toMediaTypeOrNull())
        val imagePart = homeRepository?.createMultiPartBody(uri)
        return homeRepository
            ?.updatePost(token, idPost, imagePart, body)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun progressDialogStatus(): BehaviorSubject<Boolean>? = progressBehavior


    private fun initDataRecycler(start: Int, end: Int) {
        postListRecycler.removeAt(postListRecycler.size - 1)
        for (i in start until end) {
            postListRecycler.add(postLists[i])
        }
        postListRecycler.add(PostItem())
    }
}