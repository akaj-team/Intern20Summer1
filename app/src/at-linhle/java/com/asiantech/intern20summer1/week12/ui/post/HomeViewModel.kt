package com.asiantech.intern20summer1.week12.ui.post

import android.os.Handler
import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.data.model.LikeResponse
import com.asiantech.intern20summer1.week12.data.model.Post
import com.asiantech.intern20summer1.week12.data.source.HomeRepository
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

class HomeViewModel(private val repository: HomeRepository) : ViewModel(),
    HomeVMContract {

    companion object {
        private const val LIMIT_ITEM = 10
        internal const val DELAY_TIME = 2000L
    }

    private var progressBar = BehaviorSubject.create<Boolean>()
    private var result = false
    private var postItems = mutableListOf<Post?>()
    private var postItemsStorage = mutableListOf<Post>()
    private var isLoading = false
    private var tokenVM: String? = null
    override fun getAllPost(): MutableList<Post> = postItemsStorage

    override fun getListPostAdapter(): MutableList<Post?> {
        createListPost()
        return postItems
    }

    override fun getListPost(): MutableList<Post?> = postItems

    override fun getListPostFromServer(token: String) =
        repository.getListPost(token)?.doOnSuccess {
            if (it.isSuccessful) {
                tokenVM = token
                postItemsStorage.clear()
                postItemsStorage.addAll(it.body()?.toMutableList() ?: mutableListOf())
            }
        }

    override fun updateLikePost(
        token: String,
        id: Int,
        position: Int
    ): Single<Response<LikeResponse>>? =
        repository.updateLikePost(token, id)?.doOnSuccess {
            if (it.isSuccessful) {
                it.body()?.let { post ->
                    postItems[position]?.likeCount = post.likeCount
                    postItems[position]?.likeFlag = post.likeFlag
                }
            }
        }

    override fun refreshData() = postItems.clear()

    override fun loadMore(lastVisibleItem: Int) {
        if (canLoadMore(lastVisibleItem)) {
            Handler().postDelayed({
                var currentSize = postItems.size
                val nextLimit = currentSize + LIMIT_ITEM
                while (currentSize < postItemsStorage.size && currentSize < nextLimit) {
                    postItems.add(postItemsStorage[currentSize])
                    currentSize++
                }
                progressBar.onNext(false)
                isLoading = false
            },
                DELAY_TIME
            )
            isLoading = true
            progressBar.onNext(true)
        }
    }

    override fun updateProgressBar(): BehaviorSubject<Boolean> = progressBar

    override fun searchPostFromServer(key: String): Single<Response<MutableList<Post>>>? =
        tokenVM?.let {
            repository.getListPost(it)?.doOnSuccess { response ->
                if (response.isSuccessful) {
                    postItemsStorage.clear()
                    response.body()?.forEach { post ->
                        if (post.content.contains(key)) {
                            post.content = key
                            postItemsStorage.add(post)
                        }
                    }
                    if (postItemsStorage.size != 0) {
                        result = true
                        createListPost()
                    } else {
                        result = false
                    }
                }
            }
        }

    override fun getResultSearch(): Boolean = result

    private fun canLoadMore(lastVisibleItem: Int) =
        (!isLoading && lastVisibleItem == postItems.size - 3 && postItems.size < postItemsStorage.size)

    private fun createListPost() {
        postItems.clear()
        if (postItemsStorage.size >= LIMIT_ITEM) {
            for (i in 0 until LIMIT_ITEM) {
                postItems.add(postItemsStorage[i])
            }
        } else {
            for (element in postItemsStorage) {
                postItems.add(element)
            }
        }
    }
}
