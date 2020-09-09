package com.asiantech.intern20summer1.week10

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.LoginFragment.Companion.KEY_FULL_NAME
import com.asiantech.intern20summer1.week10.LoginFragment.Companion.KEY_USER_ID
import kotlinx.android.synthetic.`at-hoangtran`.fragment_home.*
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    companion object {
        private const val DELAY_TIME = 2000L
        internal const val KEY_TOKEN = "token"
        internal fun newInstance(fullName: String?, token: String?, userId: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_FULL_NAME, fullName)
                    putString(KEY_TOKEN, token)
                    putInt(KEY_USER_ID, userId)
                }
            }
    }

    private lateinit var postItem: MutableList<Post?>
    private lateinit var postItemStorage: MutableList<Post?>
    private lateinit var adapter: PostViewHolder
    private var name: String? = null
    private var token: String? = null
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getData()
        initData()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSwipeRefresh()
    }

    private fun initAdapter() {
        postItem = postItemStorage
        adapter = PostViewHolder(postItem, userId)
        adapter.onHeartClicked = {
            handleHeartIcon(it)
        }
        adapter.onUpdateClicked = {
            handleUpdateIcon(it)
        }
        rvContainer.layoutManager = LinearLayoutManager(context)
        rvContainer.adapter = adapter
    }

    private fun handleHeartIcon(position: Int) {
        val callApi = token?.let {
            ApiClient.createPostService()?.updatePostLike(it, postItem[position]?.id ?: 0)
        }
        callApi?.enqueue(object : retrofit2.Callback<LikeResponse> {
            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        postItem[position]?.likeCount = it.likeCount
                        postItem[position]?.likeFlag = it.likeFlag
                        adapter.notifyItemChanged(position, null)
                        (rvContainer?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                            true
                    }
                }
            }
        })
    }

    private fun handleUpdateIcon(position: Int) {
        val id = postItem[position]?.id ?: 0
        val content = postItem[position]?.content
        val image = postItem[position]?.image
        (activity as HomeActivity).replaceFragment(
            UpdateFragment.newInstance(
                token,
                content,
                image,
                id
            ), true
        )
    }

    private fun getData() {
        arguments?.apply {
            name = getString(KEY_FULL_NAME)
            token = getString(KEY_TOKEN)
            userId = getInt(KEY_USER_ID)
        }
    }

    private fun initData() {
        postItemStorage = mutableListOf()
        val callApi = token?.let {
            ApiClient.createPostService()?.getListPost(it)
        }
        callApi?.enqueue(object : retrofit2.Callback<MutableList<Post>> {
            override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MutableList<Post>>,
                response: Response<MutableList<Post>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        for (i in 0 until size) {
                            postItemStorage.add(this[i])
                        }
                    }
                    progressBar.visibility = View.GONE
                    initAdapter()
                }
            }
        })
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
}
