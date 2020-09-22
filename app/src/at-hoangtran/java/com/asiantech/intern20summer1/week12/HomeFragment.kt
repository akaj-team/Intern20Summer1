package com.asiantech.intern20summer1.week12

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-hoangtran`.fragment_home.*

class HomeFragment : Fragment() {
    companion object {
        private const val DELAY_TIME = 2000L
    }

    private var postItem: MutableList<Post> = mutableListOf()
    private lateinit var postItemStorage: MutableList<Post>
    private lateinit var postItemSearch: MutableList<Post>
    private lateinit var adapter: PostViewHolder
    private var isLoading: Boolean = false
    private var token: String? = null
    private var viewModel = PostViewModel(RemoteRepository())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSwipeRefresh()
        getData()
        initData()
        handleLoadMore()
    }

    private fun initAdapter() {
        for (i in 0..10) {
            postItem.add(postItemStorage[i])
        }
        adapter = PostViewHolder(postItem)
        adapter.onHeartClicked = {
            handleHeartIcon(it)
        }
        rvContainer.layoutManager = LinearLayoutManager(context)
        rvContainer.adapter = adapter
    }

    private fun handleHeartIcon(position: Int) {
        token?.let {
            viewModel.updatePostLike(it, postItem[position].id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ response ->
                    response.body()?.apply {
                        postItem[position].likeCount = this.likeCount
                        postItem[position].likeFlag = this.likeFlag
                        adapter.notifyItemChanged(position, null)
                        (rvContainer?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                            true
                    }
                }, {

                })
        }
    }

    private fun getData() {
        token = (activity as HomeActivity).getData()
    }

    private fun initData() {
        postItemStorage = mutableListOf()
        token?.let { token ->
            viewModel.getListPost(token)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    postItemStorage = it
                    progressBar.visibility = View.GONE

                    initAdapter()
                }, {

                })
        }
    }

    private fun handleSwipeRefresh() {
        swipeContainer.setOnRefreshListener {
            Handler().postDelayed({
                postItem.clear()
                progressBar.visibility = View.VISIBLE
                initData()
                adapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    private fun handleLoadMore() {
        rvContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                if (!isLoading) {
                    linearLayoutManager?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == postItem.size - 3
                            && postItem.size < postItemStorage.size
                        ) {
                            loadMore()
                        }
                    }
                }
            }
        })
    }

    private fun loadMore() {
        if (postItem.size != 0) {
            Handler().postDelayed({
                var currentSize = postItem.size
                val nextLimit = currentSize + 10
                while (currentSize < postItemStorage.size && currentSize < nextLimit) {
                    postItem.add(postItemStorage[currentSize])
                    currentSize++
                }
                adapter.notifyDataSetChanged()

                isLoading = false
                progressBar.visibility = View.GONE
            }, 1000L)
            isLoading = true
            progressBar.visibility = View.VISIBLE
        }
    }

    internal fun search(search: String) {
        postItemSearch.clear()
        for (i in postItemStorage.indices) {
            if (postItemStorage[i].content.contains(search)) {
                postItemSearch.add(postItemStorage[i])
                postItemStorage[i].content = search
            }
        }
        if (postItemSearch.size == 0) {
            Toast.makeText(activity, "No result", Toast.LENGTH_SHORT).show()
        } else {
            postItem.clear()
            postItemStorage.clear()
            initAdapter()
            handleLoadMore()
        }
    }
}
