package com.asiantech.intern20summer1.w11.ui.fragment.home

import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.source.HomeRepository
import com.asiantech.intern20summer1.w11.data.source.LocalRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is HomeViewModel TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */

class HomeVM(
    private val homeRepository: HomeRepository?,
    private val localRepository: LocalRepository
) : HomeVMContract {

    companion object {
        private const val RECYCLER_LOAD_SIZE = 10
    }

    var postLists = mutableListOf<PostItem>()
    var postListRecycler = mutableListOf<PostItem>()


    override fun likePost(token: String, position: Int): Single<Response<ResponseLike>>? =
        homeRepository
            ?.likePost(token, postLists[position].id)
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

    override fun getIdUser(): Int? =
        localRepository.getIdUser()

    override fun getToken(): String? =
        localRepository.getToken()


    private fun initDataRecycler(start: Int, end: Int) {
        postListRecycler.removeAt(postListRecycler.size - 1)
        for (i in start until end) {
            postListRecycler.add(postLists[i])
        }
        postListRecycler.add(PostItem())
    }
}