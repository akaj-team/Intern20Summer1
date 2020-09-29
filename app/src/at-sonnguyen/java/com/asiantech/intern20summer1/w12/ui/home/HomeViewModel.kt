package com.asiantech.intern20summer1.w12.ui.home

import android.os.Handler
import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.data.model.LikeResponse
import com.asiantech.intern20summer1.w12.data.model.Post
import com.asiantech.intern20summer1.w12.data.source.repository.HomeRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

@Suppress("DEPRECATION")
class HomeViewModel(private val repository: HomeRepository) : ViewModel(), HomeVMContact {

    companion object {
        private const val ITEM_LIMIT = 10
        private const val TIME_DELAY = 2000
        private var isSearching = false
    }

    private var isLoading = false
    private var posts = mutableListOf<Post?>()
    private var allPosts = mutableListOf<Post>()
    private var progressBar = BehaviorSubject.create<Boolean>()

    override fun getAllPostsFromServer(token: String): Single<Response<MutableList<Post>>>? =
        repository.getAllPosts(token)?.doOnSuccess {
            if (it.isSuccessful) {
                allPosts.clear()
                allPosts.addAll(it.body()?.toMutableList() ?: mutableListOf())
            }
        }


    override fun likePost(token: String, id: Int): Single<Response<LikeResponse>>? =
        repository.likePost(token, id)

    override fun likePost(token: String, id: Int, position: Int): Single<Response<LikeResponse>>? =
        repository.likePost(token, id)?.doOnSuccess {
            if (it.isSuccessful) {
                it.body()?.let { post ->
                    posts[position]?.like_count = post.like_count
                    posts[position]?.like_flag = post.like_flag
                }
            }
        }

    override fun getPostList(): MutableList<Post?> = posts

    override fun getPostListAdapter(): MutableList<Post?> {
        posts.clear()
        if (allPosts.size >= ITEM_LIMIT) {
            for (i in 0 until ITEM_LIMIT) {
                posts.add(allPosts[i])
            }
        } else {
            for (post in allPosts) {
                posts.add(post)
            }
        }
        return posts
    }

    override fun refreshData() {
        posts.clear()
    }

    override fun loadMore(
        lastVisibleItem: Int
    ) {
        if (canLoadMore(lastVisibleItem)) {
            Handler().postDelayed({
                var currentSize = getPostList().size
                val nextLimit = if (allPosts.size - posts.size >= 10) {
                    currentSize + ITEM_LIMIT
                } else {
                    currentSize + allPosts.size - posts.size
                }
                while (currentSize < nextLimit) {
                    posts.add(allPosts[currentSize])
                    currentSize++
                }
                progressBar.onNext(false)
                isLoading = false
            }, TIME_DELAY.toLong())
            isLoading = true
            progressBar.onNext(true)
        }
    }

    override fun updateProgressBar(): BehaviorSubject<Boolean> = progressBar

    override fun getAllPostFromServer(): MutableList<Post> = allPosts

    private fun canLoadMore(lastVisibleItem: Int) =
        (!isLoading && lastVisibleItem == posts.size - 2 && posts.size < allPosts.size)

    override fun isEnableProgressBar() = progressBar
    override fun searchContent(key: String, token: String): Single<Response<MutableList<Post>>>? =
        repository.getAllPosts(token)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSuccess {
                if (it.isSuccessful) {
                    posts.clear()
                    allPosts.clear()
                    it.body()?.forEach { post ->
                        if (post.content.contains(key, true)) {
                            post.content = key
                            allPosts.add(post)
                        }
                    }
                    if (allPosts.isNotEmpty()) {
                        isSearching = true
                        getPostListAdapter()
                    } else {
                        isSearching = false
                    }
                }
            }

    override fun isSearching(): Boolean = isSearching
}
