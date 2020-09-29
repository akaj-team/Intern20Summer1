package com.asiantech.intern20summer1.w12.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.data.model.Post
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.data.source.repository.HomeRepository
import com.asiantech.intern20summer1.w12.ui.login.LoginFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_home.*

@Suppress("DEPRECATION")
class HomActivity : AppCompatActivity() {

    private var viewModel: HomeVMContact? = null
    private var user: User = User(0, "", "", "")
    private lateinit var postAdapter: RecyclerViewAdapter
    private var posts = mutableListOf<Post>()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        private const val DELAY_TIME = 2000
        internal const val BAD_REQUEST_CODE = 400
        internal const val SERVER_NOT_FOUND_CODE = 404
        internal const val REQUEST_TIMEOUT_CODE = 408
        internal const val IMAGE_FOLDER_URL = "https://at-a-trainning.000webhostapp.com/images/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w12_fragment_home)
        viewModel = HomeViewModel(HomeRepository())
        getDataFromLogin()
        getAllPost()
        initAdapter()
        initScrollListener()
        initListener()
        toolbarHome.title = user.full_name
    }

    private fun getAllPost() {
        user.token.let {
            viewModel?.getAllPostsFromServer(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    initAdapter()
                    progressBarMain.visibility = View.GONE
                    handleSwipeRefresh()
                    initScrollListener()
                }, {
                    // No-ops
                })
        }
    }

//    override fun onStart() {
//        super.onStart()
//        initView()
//        initListener()
//    }

    override fun onResume() {
        super.onResume()
        viewModel?.updateProgressBar()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(this::handleProgressStatus)?.let {
                compositeDisposable.add(it)
            }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

//    private fun initView() {
//        toolbarHome.title = user.full_name
//    }

    private fun handleProgressStatus(status: Boolean) {
        if (status) {
            progressBarMain?.visibility = View.VISIBLE
        } else {
            progressBarMain?.visibility = View.INVISIBLE
        }
    }

    private fun initListener() {
        handleSearchImageListener()
    }

    private fun initAdapter() {
        postAdapter =
            RecyclerViewAdapter(viewModel?.getPostListAdapter() ?: mutableListOf(), user.id)
        recyclerViewHome.layoutManager = LinearLayoutManager(this)
        recyclerViewHome.adapter = postAdapter
        postAdapter.onLikeClicked = {
            likePost(it)
        }
    }

    private fun likePost(position: Int) {
        viewModel?.likePost(user.token, viewModel?.getPostList()?.get(position)?.id ?: 0, position)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                postAdapter.notifyItemChanged(position, position)
                (recyclerViewHome?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                    true
            }, {
                //No-op
            })
    }

    private fun handleSearchImageListener() {
        imgSearch.setOnClickListener {
            val fragmentManager = supportFragmentManager
            SearchDialogFragment().show(fragmentManager, null)
        }
    }

    private fun handleSwipeRefresh() {
        pullToRefresh.setOnRefreshListener {
            Handler().postDelayed({
                posts.clear()
                getAllPost()
//                postAdapter.notifyDataSetChanged()
                pullToRefresh.isRefreshing = false
            }, DELAY_TIME.toLong())
        }
    }

    private fun initScrollListener() {
        recyclerViewHome?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                linearLayoutManager?.let {
                    val lastItem = linearLayoutManager.findLastVisibleItemPosition()
                    viewModel?.loadMore(lastItem)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                postAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun getDataFromLogin() {
        (intent?.getSerializableExtra(LoginFragment.USER_KEY_LOGIN) as? User)?.let {
            user.id = it.id
            user.email = it.email
            user.full_name = it.full_name
            user.token = it.token
        }
    }

    internal fun search(key: String) {
        viewModel?.searchContent(key, user.token)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (viewModel?.isSearching() == true) {
                    postAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, getString(R.string.w12_nothing_toast), Toast.LENGTH_SHORT)
                        .show()
                }
            }, {
                //No-op
            })
    }
}
