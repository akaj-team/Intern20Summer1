package com.asiantech.intern20summer1.week12.ui.post

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.adapters.PostViewHolder
import com.asiantech.intern20summer1.week12.data.source.HomeRepository
import com.asiantech.intern20summer1.week12.ui.login.LoginFragment.Companion.KEY_STRING_FULL_NAME
import com.asiantech.intern20summer1.week12.ui.login.LoginFragment.Companion.SHARED_PREFERENCE_TOKEN
import com.asiantech.intern20summer1.week12.ui.post.HomeViewModel.Companion.DELAY_TIME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.`at-linhle`.activity_post_home.*

class HomeRxActivity : AppCompatActivity() {

    private var fullName: String? = null
    private var token: String? = null
    private lateinit var adapter: PostViewHolder
    private var viewModel: HomeVMContract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_home)
        viewModel = HomeViewModel(
            HomeRepository()
        )
        getData()
        initData()
        handleSearchIconCLicked()
        toolbarHome?.title = fullName
    }

    private fun getData() {
        fullName = intent?.getStringExtra(KEY_STRING_FULL_NAME)
        token = intent?.getStringExtra(SHARED_PREFERENCE_TOKEN)
    }

    private fun initAdapter() {
        adapter = PostViewHolder(viewModel?.getListPostAdapter() ?: mutableListOf())
        recyclerViewContainer?.layoutManager = LinearLayoutManager(this)
        adapter.onHeartClicked = {
            handleClickingHeartIcon(it)
        }
        recyclerViewContainer?.adapter = adapter
    }

    private fun initData() {
        token?.let {
            viewModel?.getListPostFromServer(it)
                ?.subscribeOn(io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    initAdapter()
                    progressLoadApi?.visibility = View.GONE
                    handleSwipeRefresh()
                    initScrollListener()
                }, {
                    //No-op
                })
        }
    }

    private fun handleSwipeRefresh() {
        swipeContainer.setOnRefreshListener {
            Handler().postDelayed({
                viewModel?.refreshData()
                imgPlus?.visibility = View.VISIBLE
                progressLoadApi?.visibility = View.VISIBLE
                initData()
                adapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            }, DELAY_TIME)
        }
    }

    private fun initScrollListener() {
        recyclerViewContainer?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                linearLayoutManager?.let {
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    viewModel?.loadMore(lastVisibleItem)
                    viewModel?.updateProgressBar()?.subscribe({
                        if (it) {
                            progressBar?.visibility = View.VISIBLE
                        } else {
                            progressBar?.visibility = View.INVISIBLE
                        }
                    }, {
                        //No-op
                    })
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun handleClickingHeartIcon(position: Int) {
        token?.let {
            viewModel?.updateLikePost(
                it,
                viewModel?.getListPost()?.get(position)?.id ?: 0,
                position
            )
                ?.subscribeOn(io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    adapter.notifyItemChanged(position, null)
                    (recyclerViewContainer?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                        true
                }, {
                    //No-op
                })
        }
    }

    private fun handleSearchIconCLicked() {
        imgPlus?.setOnClickListener {
            val fragmentManager = supportFragmentManager
            SearchDialogFragment()
                .show(fragmentManager, null)
        }
    }

    internal fun search(search: String) {
        viewModel?.searchPostFromServer(search)
            ?.subscribeOn(io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (viewModel?.getResultSearch() == true) {
                    adapter.notifyDataSetChanged()
                    imgPlus?.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.home_rx_activity_search_result),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, {
                //No-op
            })
    }
}
