package com.asiantech.intern20summer1.w12.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.activity.HomeActivity
import com.asiantech.intern20summer1.w12.adapter.RecyclerViewAdapter
import com.asiantech.intern20summer1.w12.model.Post
import com.asiantech.intern20summer1.w12.model.User
import com.asiantech.intern20summer1.w12.view_model.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_home.*

@Suppress("DEPRECATION", "NAME_SHADOWING")
class HomeFragment : Fragment() {
    private var user = User(0, "", "", "")
    private var posts = mutableListOf<Post>()
    private lateinit var postAdapter: RecyclerViewAdapter

    companion object {
        internal const val USER_KEY = "user-Key"
        private const val DELAY_TIME = 2000
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
        setHasOptionsMenu(true)
        getDataFromActivity()
    }

    private fun initView() {
        toolbarHome.title = user.full_name
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
        getAllPost()
        initView()
        initAdapter()
        initListener()
    }

    private fun likePost() {
        postAdapter.onLikeClicked = { position ->
            HomeViewModel().likePost(user.token, posts[position].id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        it.body().let { likeResponse ->
                            likeResponse?.let {
                                posts[position].like_count = likeResponse.like_count
                                posts[position].like_flag = likeResponse.like_flag
                            }
                            postAdapter.notifyItemChanged(position, null)
                            (recyclerViewHome.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                                true
                        }
                    } else {
                        Log.d("TAG0000", "likePost: ${it.code()}")
                    }
                }, {
                    // No-ops
                })
        }
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
        likePost()
        handleSwipeRefresh()
        handleAddImageViewListener()
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
            HomeViewModel().getAllPost(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ response ->
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            for (i in 0 until size) {
                                posts.add(this[i])
                            }
                        }
                        progressBarMain.visibility = View.GONE
                        initAdapter()
                    }
                }, {
                    // No-ops
                })
        }
    }

    private fun initAdapter() {
        postAdapter = RecyclerViewAdapter(posts, user.id)
        recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewHome.adapter = postAdapter
        postAdapter.onUpdateClicked = {
            handleUpdateListener(it)
        }
        likePost()
    }

//    private fun displayErrorDialog(message: String) {
//        val dialog = AlertDialog.Builder(requireContext())
//        dialog.setTitle(getString(R.string.w10_error_dialog_title))
//            .setMessage(message)
//            .setPositiveButton(getString(R.string.w10_ok_button_text)) { dialog, _ -> dialog.dismiss() }
//            .show()
//    }
}
