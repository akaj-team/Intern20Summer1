package com.asiantech.intern20summer1.week12.ui.home

import com.asiantech.intern20summer1.week12.data.models.ApiResponse
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import com.asiantech.intern20summer1.week12.data.source.LocalRepository
import com.asiantech.intern20summer1.week12.data.source.PostRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.Response

class PostViewModel(
    private val postRepository: PostRepository,
    private val localRepository: LocalRepository
) : PostVMContract {
    var postLists = mutableListOf<NewPost>()
    var postListRecycler = mutableListOf<NewPost>()
    private var itemSearch = mutableListOf<NewPost>()

    override fun getDataRecyclerView() = postLists

    override fun getPost(): Single<Response<MutableList<NewPost>>>? =
        postRepository.getPost(localRepository.getToken())
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSuccess {
                postListRecycler.clear()
                postLists.clear()
                it.body()?.let { it1 -> postLists.addAll(it1) }
                postListRecycler.add(NewPost())
            }

    override fun likePost(token: String, id: Int): Single<Response<ResponseLike>>? =
        postRepository.likePost(token, postLists[id].id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSuccess {
                if (it.isSuccessful) {
                    it.body()?.let {
                        postLists[id].like_count = it.likeCount
                        postLists[id].like_flag = it.like_flag
                    }
                }
            }


    override fun createNewPost(
        token: String,
        body: Post,
        image: MultipartBody.Part?
    ): Single<Response<ApiResponse>>? = postRepository.createNewPost(token, body, image)
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.doOnSuccess {
        }

    override fun getIdUser(): Int? =
        localRepository.getIdUser()

    override fun getToken(): String? = localRepository.getToken()

    override fun search(search: String): MutableList<NewPost> {
        itemSearch.clear()
        for (i in postLists.indices) {
            if (postLists[i].content.contains(search)) {
                itemSearch.add(postLists[i])
            }
        }
        return itemSearch
    }
}
