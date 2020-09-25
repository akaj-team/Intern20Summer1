package com.asiantech.intern20summer1.week12.ui.home

import android.os.Handler
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import com.asiantech.intern20summer1.week12.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.week12.data.source.datasource.PostDataSource
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

class PostViewModel(
    private val postRepository: PostDataSource,
    private val localRepository: LocalDataSource
) : PostVMContract {
    companion object {
        private const val POSITION_ITEM_LOAD_MORE = 3
        private const val LIMIT_ITEM = 10
        internal const val DELAY_TIME = 2000L
    }

    private var progressBar = BehaviorSubject.create<Boolean>()
    private var result = false
    private var postItems = mutableListOf<NewPost?>()
    private var postItemsAll = mutableListOf<NewPost>()
    private var isLoading = false
    private var tokenPost: String? = null
    override fun getAllPost(): MutableList<NewPost> = postItemsAll

    override fun getListPostAdapter(): MutableList<NewPost?> {
        createListPost()
        return postItems
    }

    override fun getListPost(): MutableList<NewPost?> = postItems

    override fun getListPostFromServer(token: String): Single<Response<MutableList<NewPost>>>? =
        postRepository.getPost(token)?.doOnSuccess {
            if (it.isSuccessful) {
                tokenPost = token
                postItemsAll.clear()
                postItemsAll.addAll(it.body()?.toMutableList() ?: mutableListOf())
            }
        }

    override fun updateLikePost(
        token: String,
        id: Int
    ): Single<Response<ResponseLike>>? =
        postItems[id]?.id?.let {
            postRepository.likePost(token, it)?.doOnSuccess {
                if (it.isSuccessful) {
                    it.body().let {
                        postItems[id]?.like_flag = it?.like_flag!!
                        postItems[id]?.like_count = it.likeCount
                    }
                }
            }
        }

    override fun refreshData() = postItems.clear()

    override fun loadMore(positionItem: Int) {
        if (canLoadMore(positionItem)) {
            Handler().postDelayed({
                var currentSize = postItems.size
                val next = currentSize + LIMIT_ITEM
                while (currentSize < postItemsAll.size && currentSize < next) {
                    postItems.add(postItemsAll[currentSize])
                    currentSize++
                }
                progressBar.onNext(false)
                isLoading = false
            }, DELAY_TIME)
            isLoading = true
            progressBar.onNext(true)
        }
    }

    override fun updateProgressBar(): BehaviorSubject<Boolean> = progressBar

    override fun searchPostFromServer(key: String): Single<Response<MutableList<NewPost>>>? =
        tokenPost.let {
            it?.let { it1 ->
                postRepository.getPost(it1)?.doOnSuccess {
                    if (it.isSuccessful) {
                        postItemsAll.clear()
                        it.body()?.forEach { post ->
                            if (post.content.contains(key)) {
                                post.content = key
                                postItemsAll.add(post)
                            }
                        }
                        if (postItemsAll.size != 0) {
                            result = true
                            createListPost()
                        } else {
                            result = false
                        }
                    }
                }
            }
        }

    override fun getResultSearch(): Boolean = result
    override fun getIdUser(): Int? =
        localRepository.getIdUser()

    override fun getToken(): String? = localRepository.getToken()

    private fun canLoadMore(lastVisibleItem: Int) =
        (!isLoading && lastVisibleItem == postItems.size - POSITION_ITEM_LOAD_MORE && postItems.size < postItemsAll.size)

    private fun createListPost() {
        postItems.clear()
        if (postItemsAll.size >= LIMIT_ITEM) {
            for (i in 0 until LIMIT_ITEM) {
                postItems.add(postItemsAll[i])
            }
        } else {
            for (element in postItemsAll) {
                postItems.add(element)
            }
        }
    }
}
