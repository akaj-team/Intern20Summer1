package com.asiantech.intern20summer1.w10.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import com.asiantech.intern20summer1.w10.adapter.RecyclerAdapter
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiPostService
import com.asiantech.intern20summer1.w10.models.PostItem
import com.asiantech.intern20summer1.w10.models.ResponseLike
import com.asiantech.intern20summer1.w10.models.ResponsePost
import com.asiantech.intern20summer1.w10.utils.AppUtils
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_home.*
import retrofit2.Call
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is HomeFragment class. It is fragment to display home page
 */

class HomeFragment : Fragment() {

    companion object {
        private const val DELAY_LOAD_MORE = 2000L
        private const val RECYCLER_LOAD_SIZE = 10
        internal fun newInstance() = HomeFragment()
    }

    private var callApi: ApiPostService? = null
    var postLists = mutableListOf<PostItem>()
    var postListRecycler = mutableListOf<PostItem>()
    var postAdapter = RecyclerAdapter(postListRecycler)
    private var isLoadMore = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callApi = Api.getInstance()?.create(ApiPostService::class.java)
        return inflater.inflate(R.layout.w10_fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initRecyclerView()
        initListener()
    }

    private fun initRecyclerView() {
        initData()
        initAdapter()
        handleForLoadMoreAndRefreshListener()
    }

    private fun initListener() {
        imgAddPost?.setOnClickListener {
            handleShowDialogPostFragment()
        }
    }

    private fun initData(isSwiped: Boolean = false) {
        if (!isSwiped) {
            progressBar?.visibility = View.VISIBLE
        }
        val token = AppUtils().getToken(requireContext())
        callApi?.getPostLists(token)?.enqueue(object : retrofit2.Callback<List<PostItem>> {
            override fun onResponse(
                call: Call<List<PostItem>>,
                response: Response<List<PostItem>>
            ) {
                postLists.clear()
                postListRecycler.clear()
                postListRecycler.add(PostItem())
                response.body()?.toCollection(postLists)
                if (postLists.size < RECYCLER_LOAD_SIZE) {
                    initDataRecycler(0, postLists.size)
                } else {
                    initDataRecycler(0, RECYCLER_LOAD_SIZE)
                }
                postAdapter.notifyDataSetChanged()
                (recyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = true
                progressBar?.visibility = View.INVISIBLE
                swipeRefreshContainer.isRefreshing = false
            }

            override fun onFailure(call: Call<List<PostItem>>, t: Throwable) {}
        })
        postAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {

        postAdapter.onLikeClicked = { position ->
            val token = AppUtils().getToken(requireContext())
            callApi?.likePost(token, postLists[position].id)
                ?.enqueue(object : retrofit2.Callback<ResponseLike> {
                    override fun onResponse(
                        call: Call<ResponseLike>,
                        response: Response<ResponseLike>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                postLists[position].like_flag = it.like_flag
                                postLists[position].like_count = it.like_count
                            }
                            postAdapter.notifyItemChanged(position, null)
                            (recyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                                true
                        }
                    }

                    override fun onFailure(call: Call<ResponseLike>, t: Throwable) {}
                })
        }
        postAdapter.onMenuClicked = { view, position ->
            showMenuItem(view, position)
        }

        recyclerView.adapter = postAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    private fun showMenuItem(view: View, position: Int) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.w10_menu_item, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuDelete -> {
                    deletePost(position)
                }
                R.id.menuUpdate -> {
                    handleShowDialogUpdateFragment(postLists[position])
                }
            }
            return@setOnMenuItemClickListener false
        }
        popupMenu.show()
    }

    private fun deletePost(position: Int) {
        val token = AppUtils().getToken(requireContext())
        val id = postLists[position].id
        callApi?.deletePost(token, id)
            ?.enqueue(object : retrofit2.Callback<ResponsePost> {
                override fun onResponse(
                    call: Call<ResponsePost>,
                    response: Response<ResponsePost>
                ) {
                    if (response.isSuccessful) {
                        postLists.removeAt(position)
                        postAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ResponsePost>, t: Throwable) {}
            })
    }

    private fun handleShowDialogPostFragment() {
        val fragmentManager = (activity as ApiMainActivity).supportFragmentManager
        val fragment = PostDialogFragment.newInstance()
        fragment.onPostClick = {
            initData()
        }
        fragment.show(fragmentManager, null)
    }

    private fun handleShowDialogUpdateFragment(postItem: PostItem) {
        val fragmentManager = (activity as ApiMainActivity).supportFragmentManager
        val fragment = UpdateDialogFragment.newInstance(postItem)
        fragment.onPostClick = {
            initData()
        }
        fragment.show(fragmentManager, null)
    }


    private fun handleForLoadMoreAndRefreshListener() {
        swipeRefreshContainer.setOnRefreshListener {
            initData(true)
            postAdapter.notifyDataSetChanged()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoadMore && (lastVisibleItem == postListRecycler.size - 1)) {
                    isLoadMore = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        val currentSize = postListRecycler.size - 1
                        if (postLists.size - currentSize < RECYCLER_LOAD_SIZE) {
                            initDataRecycler(currentSize, postLists.size)
                        } else {
                            initDataRecycler(currentSize, currentSize + RECYCLER_LOAD_SIZE)
                        }
                        postAdapter.notifyDataSetChanged()
                    }, DELAY_LOAD_MORE)
                }
                isLoadMore = false
            }
        })
    }

    private fun initDataRecycler(start: Int, end: Int) {
        postListRecycler.removeAt(postListRecycler.size - 1)
        for (i in start until end) {
            postListRecycler.add(postLists[i])
        }
        postListRecycler.add(PostItem())
    }
}
