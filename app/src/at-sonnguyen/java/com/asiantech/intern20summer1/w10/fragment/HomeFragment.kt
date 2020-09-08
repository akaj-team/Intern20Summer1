package com.asiantech.intern20summer1.w10.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.HomeActivity
import com.asiantech.intern20summer1.w10.adapter.RecyclerViewAdapter
import com.asiantech.intern20summer1.w10.api.APIClient
import com.asiantech.intern20summer1.w10.api.PostAPI
import com.asiantech.intern20summer1.w10.data.LikeResponse
import com.asiantech.intern20summer1.w10.data.Post
import com.asiantech.intern20summer1.w10.data.User
import kotlinx.android.synthetic.`at-sonnguyen`.w10_fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private var user = User(0, "", "", "")
    private var posts = mutableListOf<Post>()
    private lateinit var postAdapter: RecyclerViewAdapter
    private var isLoading = false
    private var currentPosition = -1

    companion object {
        internal const val USER_KEY = "user-Key"
        private const val DELAY_TIME = 2000
        internal fun newInstance(user: User): HomeFragment {
            val homeFragment: HomeFragment = HomeFragment()
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

    private fun initView(){
        toolbarHome.title = user.full_name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_home, container, false)
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
            val service = APIClient.createServiceClient()?.create(PostAPI::class.java)
            val call = service?.likePost(user.token, posts[position].id)
            call?.enqueue(object : Callback<LikeResponse> {
                override fun onResponse(
                    call: Call<LikeResponse>,
                    response: Response<LikeResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            posts[position].like_count = it.like_count
                            posts[position].like_flag = it.like_flag
                        }
                        postAdapter.notifyItemChanged(position, null)
                        (recyclerViewHome.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                            true
                    }
                }

                override fun onFailure(call: Call<LikeResponse>, t: Throwable) {}
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

    private fun handleAddImageViewListener(){
        imgAdd.setOnClickListener {
            (activity as? HomeActivity)?.replaceFragmentHome(AddPostFragment.newInstance(user),true)
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
                user.token
            ),true
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
        val service = APIClient.createServiceClient()?.create(PostAPI::class.java)
        val call = service?.getAllPost(user.token)
        call?.enqueue(object : Callback<MutableList<Post>> {
            override fun onResponse(
                call: Call<MutableList<Post>>,
                response: Response<MutableList<Post>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        it.forEach {post ->
                            posts.add(post)
                        }
                        postAdapter = RecyclerViewAdapter(posts,user.id)
                        initAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                t.message?.let {
                    displayErrorDialog(it)
                }
            }
        })
    }

    private fun initAdapter() {
        postAdapter = RecyclerViewAdapter(posts,user.id)
        recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewHome.adapter = postAdapter
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        ResourcesCompat.getDrawable(
            resources,
            R.drawable.w10_bg_recycler_view_divider_decoration,
            null
        )
            ?.let { dividerItemDecoration.setDrawable(it) }
        recyclerViewHome.addItemDecoration(dividerItemDecoration)
        postAdapter.onUpdateClicked = {
            handleUpdateListener(it)
        }
        handleLikeListener()
    }

    private fun handleLikeListener(){
        postAdapter.onLikeClicked = { position ->
            val service = APIClient.createServiceClient()?.create(PostAPI::class.java)
            val call = service?.likePost(user.token, posts[position].id)
            call?.enqueue(object : Callback<LikeResponse> {
                override fun onResponse(
                    call: Call<LikeResponse>,
                    response: Response<LikeResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            posts[position].like_count = it.like_count
                            posts[position].like_flag = it.like_flag
                        }
                        postAdapter.notifyItemChanged(position, null)
                        (recyclerViewHome.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                            true
                    }
                }

                override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                }
            })
        }
    }

    private fun displayErrorDialog(message: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(R.string.w10_error_dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.w10_ok_button_text)) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
