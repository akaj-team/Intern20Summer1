package com.asiantech.intern20summer1.w11.ui.fragment

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
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.repository.LocalRepository
import com.asiantech.intern20summer1.w11.data.repository.RemoteRepository
import com.asiantech.intern20summer1.w11.ui.activity.ApiMainActivity
import com.asiantech.intern20summer1.w11.ui.adapter.RecyclerAdapter
import com.asiantech.intern20summer1.w11.ui.viewmodel.ViewModel
import com.asiantech.intern20summer1.w11.utils.extension.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_home.*

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

    private var viewModel: ViewModel? = null
    var postLists = mutableListOf<PostItem>()
    var postListRecycler = mutableListOf<PostItem>()
    lateinit var postAdapter: RecyclerAdapter
    private var isLoadMore = false
    private var isSearching = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
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
        initAdapter()
        initData()
        handleForLoadMoreAndRefreshListener()
    }

    private fun initListener() {
        imgAddPost?.setOnClickListener {
            handleShowDialogPostFragment()
        }

        imgSearch?.setOnClickListener {
            if (!isSearching) {
                enableSearch()
            } else {
                val text = edtSearch?.text.toString()
                if (text.isNotEmpty()) {
                    searchData(text)
                }
                disableSearch()
            }
        }

        btnBackSearch?.setOnClickListener {
            disableSearch()
        }
    }

    private fun initData(isSwiped: Boolean = false) {
        if (!isSwiped) {
            progressBar?.visibility = View.VISIBLE
        }
        val token = viewModel?.getToken().toString()
        viewModel
            ?.getPosts(token)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { response ->
                postLists.clear()
                postListRecycler.clear()
                response.body()?.toCollection(postLists)
                postListRecycler.add(PostItem())
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

    }

    private fun initAdapter() {
        postAdapter = RecyclerAdapter(postListRecycler, viewModel?.getIdUser() ?: 0)
        postAdapter.onLikeClicked = { position ->
            val token = viewModel?.getToken().toString()
            viewModel
                ?.likePost(token, postLists[position].id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { response ->
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
                    "Api không hỗ trợ!!".showToast(requireContext())
                }
                R.id.menuUpdate -> {
                    handleShowDialogUpdateFragment(postLists[position])
                }
            }
            return@setOnMenuItemClickListener false
        }
        popupMenu.show()
    }

    private fun handleShowDialogPostFragment() {
        val fragmentManager = (activity as ApiMainActivity).supportFragmentManager
        val fragment = PostDialogFragment.newInstance()
        fragment.onPostClick = {
            initData()
        }
        fragment.show(fragmentManager, null)
    }

    private fun enableSearch() {
        edtSearch?.visibility = View.VISIBLE
        btnBackSearch?.visibility = View.VISIBLE
        tvTitle?.visibility = View.INVISIBLE
        imgAddPost?.visibility = View.INVISIBLE
        isSearching = true
    }

    private fun disableSearch() {
        edtSearch?.text = null
        edtSearch?.visibility = View.INVISIBLE
        btnBackSearch?.visibility = View.INVISIBLE
        tvTitle?.visibility = View.VISIBLE
        imgAddPost?.visibility = View.VISIBLE
        isSearching = false
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

    private fun searchData(text: String) {
        val token = viewModel?.getToken().toString()

        viewModel
            ?.getPosts(token)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.isSuccessful) {
                    postListRecycler.clear()
                    postLists.clear()
                    it.body()?.forEach { post ->
                        if (post.content.contains(text,true)) {
                            postLists.add(post)
                        }
                    }
                    postListRecycler.add(PostItem())
                    if (postLists.size < RECYCLER_LOAD_SIZE) {
                        initDataRecycler(0, postLists.size)
                    } else {
                        initDataRecycler(0, RECYCLER_LOAD_SIZE)
                    }
                    postAdapter.notifyDataSetChanged()
                }
            }
    }

    private fun initDataRecycler(start: Int, end: Int) {
        postListRecycler.removeAt(postListRecycler.size - 1)
        for (i in start until end) {
            postListRecycler.add(postLists[i])
        }
        postListRecycler.add(PostItem())
    }

    private fun setupViewModel() {
        viewModel = ViewModel(RemoteRepository(requireContext()), LocalRepository(requireContext()))
    }
}
