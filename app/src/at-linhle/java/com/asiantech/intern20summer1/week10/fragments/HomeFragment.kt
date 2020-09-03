package com.asiantech.intern20summer1.week10.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.adapters.PostViewHolder
import com.asiantech.intern20summer1.week10.api.ApiClient
import com.asiantech.intern20summer1.week10.models.Post
import kotlinx.android.synthetic.`at-linhle`.fragment_api_home.*
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    companion object {
        private const val KEY_STRING_FULL_NAME = "fullName"
        private const val KEY_STRING_TOKEN = "token"
        internal fun newInstance(fullName: String?, token: String?) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_STRING_FULL_NAME, fullName)
                putString(KEY_STRING_TOKEN, token)
            }
        }

        private const val LAST_ITEM_POSITION = 10
        private const val DELAY_TIME = 2000L
    }

    private lateinit var postItems: MutableList<Post?>
    private lateinit var postItemsStorage: MutableList<Post?>
    private lateinit var adapter: PostViewHolder
    private var fullName: String? = null
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getData()
        initData()
        return inflater.inflate(R.layout.fragment_api_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSwipeRefresh()
        toolbarHome.title = fullName
    }

    private fun initAdapter() {
        postItems = postItemsStorage
        adapter = PostViewHolder(postItems)
//        adapter.onHeartClicked = { position ->
//            postItems[position]?.let {
//                if (it.isLiked) {
//                    it.isLiked = false
//                    it.countLike--
//                } else {
//                    it.isLiked = true
//                    it.countLike++
//                }
//                adapter.notifyItemChanged(position)
//            }
//        }
        recyclerViewContainer.layoutManager = LinearLayoutManager(context)
        recyclerViewContainer.adapter = adapter
    }

    private fun getData() {
        arguments?.apply {
            fullName = getString(KEY_STRING_FULL_NAME)
            token = getString(KEY_STRING_TOKEN)
        }
    }

    private fun initData() {
        postItemsStorage = mutableListOf()
        val callApi = token?.let { ApiClient.cretePostService()?.getListPost(it) }
        callApi?.enqueue(object : retrofit2.Callback<MutableList<Post>> {
            override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MutableList<Post>>,
                response: Response<MutableList<Post>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        for (i in 0 until size) {
                            postItemsStorage.add(this[i])
                        }
                    }
                    progressLoadApi.visibility = View.GONE
                    initAdapter()
                }
            }

        })
    }

    private fun handleSwipeRefresh(){
        swipeContainer.setOnRefreshListener {
            Handler().postDelayed({
                postItems.clear()
                progressLoadApi.visibility = View.VISIBLE
                initData()
                adapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            }, DELAY_TIME)
        }
    }
}
