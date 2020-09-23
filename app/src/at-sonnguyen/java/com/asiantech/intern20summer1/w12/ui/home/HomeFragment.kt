package com.asiantech.intern20summer1.w12.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.activity.HomeActivity
import com.asiantech.intern20summer1.w12.data.model.Post
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.fragment.SearchDialogFragment
import com.asiantech.intern20summer1.w12.remoteRepository.RemoteRepository
import com.asiantech.intern20summer1.w12.ui.addPost.AddPostFragment
import com.asiantech.intern20summer1.w12.ui.updatePost.UpdatePostFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_home.*

@Suppress("DEPRECATION", "NAME_SHADOWING")
class HomeFragment : Fragment() {
    private var user = User(0, "", "", "")
    private var posts = mutableListOf<Post>()
    private var allPosts = mutableListOf<Post>()
    private lateinit var postAdapter: RecyclerViewAdapter
    private var isLoading = false
    private var viewModel: HomeVMContact? = null

    companion object {
        internal const val USER_KEY = "user-Key"
        private const val DELAY_TIME = 2000
        private const val ITEM_LIMIT = 10
        internal fun newInstance(user: User): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putSerializable(USER_KEY, user)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = HomeViewModel(RemoteRepository())
        setHasOptionsMenu(true)
        getDataFromActivity()
        getAllPost()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w12_fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        toolbarHome.title = user.full_name
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

    private fun getDataFromActivity() {
        (arguments?.getSerializable(USER_KEY) as? User)?.let {
            user.id = it.id
            user.email = it.email
            user.full_name = it.full_name
            user.token = it.token
        }
    }

    private fun initListener() {
//        likePost()
//        handleSwipeRefresh()
        handleAddImageViewListener()
        handleSearchImageViewListener()
    }

    private fun handleSearchImageViewListener() {
        imgSearch.setOnClickListener {
            activity?.supportFragmentManager?.let {
                val dialogFragment = SearchDialogFragment.newInstance(user)
                dialogFragment.setTargetFragment(newInstance(user), 105)
                dialogFragment.show(it, "")
            }
        }
    }

    private fun handleAddImageViewListener() {
        imgAdd.setOnClickListener {
            (activity as? HomeActivity)?.replaceFragmentHome(
                AddPostFragment.newInstance(user),
                true
            )
        }
    }

    private fun handleUpdateListener(position: Int) {
        val id = posts[position].id
        val content = posts[position].content
        val imageString = posts[position].image
        (activity as? HomeActivity)?.replaceFragmentHome(
            UpdatePostFragment.newInstance(
                id,
                imageString,
                content,
                user.token,
                user
            ), true
        )
    }

    private fun handleSwipeRefresh() {
        pullToRefresh.setOnRefreshListener {
            Handler().postDelayed({
                posts.clear()
                getAllPost()
                postAdapter.notifyDataSetChanged()
                pullToRefresh.isRefreshing = false
            }, DELAY_TIME.toLong())
        }
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

    private fun initAdapter() {
        postAdapter =
            RecyclerViewAdapter(viewModel?.getPostListAdapter() ?: mutableListOf(), user.id)
        recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewHome.adapter = postAdapter
        postAdapter.onUpdateClicked = {
            handleUpdateListener(it)
        }
        postAdapter.onLikeClicked = {
            likePost(it)
        }
    }

//    private fun displayErrorDialog(message: String) {
//        val dialog = AlertDialog.Builder(requireContext())
//        dialog.setTitle(getString(R.string.w10_error_dialog_title))
//            .setMessage(message)
//            .setPositiveButton(getString(R.string.w10_ok_button_text)) { dialog, _ -> dialog.dismiss() }
//            .show()
//    }

    private fun initScrollListener() {
        recyclerViewHome?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                linearLayoutManager?.let {
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    viewModel?.loadMore(
                        lastVisibleItem
                    )
                    viewModel?.isEnableProgressBar()?.subscribe({
                        if(it){
                            progressBarMain?.visibility = View.VISIBLE
                        }else{
                            progressBarMain?.visibility = View.INVISIBLE
                        }
                    },{})
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                postAdapter.notifyDataSetChanged()
            }
        })
    }

//    private fun initLoadMore() {
//        Handler().postDelayed({
//            var currentSize = posts.size
//            val nextLimit = if (allPosts.size - posts.size >= 10) {
//                currentSize + ITEM_LIMIT
//            } else {
//                currentSize + allPosts.size - posts.size
//            }
//            while (currentSize < nextLimit) {
//                posts.add(allPosts[currentSize])
//                currentSize++
//            }
//            postAdapter.notifyDataSetChanged()
//            isLoading = false
//            progressBarMain.visibility = View.INVISIBLE
//        }, DELAY_TIME.toLong())
//        isLoading = true
//        progressBarMain?.visibility = View.VISIBLE
//    }
}
