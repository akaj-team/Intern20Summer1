@file:Suppress("DEPRECATION")

package com.asiantech.intern20summer1.fragment.w10

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.RecyclerViewNewFeed
import com.asiantech.intern20summer1.adapter.w10.ItemFeedAdapter
import com.asiantech.intern20summer1.api.w10.ClientAPI
import com.asiantech.intern20summer1.model.w10.ApiResponse
import com.asiantech.intern20summer1.model.w10.NewPost
import com.asiantech.intern20summer1.model.w10.ResponseLike
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_new_feed.*
import retrofit2.Call
import retrofit2.Response

class NewFeedFragment : Fragment() {
    private var newfeeds = mutableListOf<NewPost>()
    private var adapterNewFeeds = ItemFeedAdapter(newfeeds)
    private var isLoading = false
    private var currentPos = -1
    internal var token: String? = null

    companion object {
        private const val DELAY_TIME = 2000L
        internal fun newInstance() = NewFeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_fragment_new_feed, container, false)
        val toolbar = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.tbNewFeed)
        (activity as RecyclerViewNewFeed).setSupportActionBar(toolbar)
        val imgPlus = view.findViewById<ImageView>(R.id.imgPlus)
        getToken()
        imgPlus?.setOnClickListener {
            Bundle().let {
                it.putString("token", token)
                val addNewFeedFragment = AddNewFeedFragment()
                addNewFeedFragment.arguments = it
                (activity as? RecyclerViewNewFeed)?.openFragment(addNewFeedFragment, true)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListAPI()
        initListener()
    }

    private fun initAdapter() {
        recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewMain.adapter = adapterNewFeeds
        Log.d("sdsdsds", "initAdapter: $token")
        adapterNewFeeds.onItemClicked = { position ->
            addLike(position)
            initListener()
        }
        adapterNewFeeds.onItemDeleteClicked = {
            displayDeleteDialog(newfeeds[it].id)
            currentPos = it
        }
        itemOnclick(adapterNewFeeds)
    }

    private fun addLike(position: Int) {
        token?.let { ClientAPI.createPost()?.likePost(it, newfeeds[position].id) }
            ?.enqueue(object : retrofit2.Callback<ResponseLike> {
                override fun onResponse(
                    call: Call<ResponseLike>,
                    response: Response<ResponseLike>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            newfeeds[position].like_count = it.likeCount
                            newfeeds[position].like_flag = it.like_flag
                            adapterNewFeeds.notifyItemChanged(position, null)
                            (recyclerViewMain.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                                true
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLike>, t: Throwable) {
                }
            })

    }

    private fun getToken() {
        val bundle = arguments
        if (bundle != null) {
            token = bundle.getString("data").toString()
        } else {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListener() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                newfeeds.clear()
                getListAPI()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, DELAY_TIME)
        }

        recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == newfeeds.size - 1) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            adapterNewFeeds.notifyDataSetChanged()
                            isLoading = false
                            progressBar.visibility = View.INVISIBLE
                        }, DELAY_TIME)
                    }
                }
            }
        })
    }

    internal fun displayErrorDialog(message: String) {
        val errorDialog = AlertDialog.Builder(requireContext())
        errorDialog.setTitle(
            getString(R.string.dialog_title_error)
        )
            .setMessage(message)
            .setPositiveButton(R.string.dialog_text_ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun getListAPI() {
        val call = token?.let { ClientAPI.createPost()?.getPost(it) }
        call?.enqueue(object : retrofit2.Callback<MutableList<NewPost>> {
            override fun onFailure(call: Call<MutableList<NewPost>>, t: Throwable) {
                t.message?.let { it -> displayErrorDialog(it) }
            }

            override fun onResponse(
                call: Call<MutableList<NewPost>>,
                response: Response<MutableList<NewPost>>
            ) {
                response.body().let {
                    it?.let { it1 -> newfeeds.addAll(it1) }
                }
                progressLoadData.visibility = View.GONE
                initAdapter()
            }
        })
    }

    private fun deleteNewFeed(id: Int) {
        val call = token?.let { ClientAPI.createPost()?.deletePosts(it, id) }
        call?.enqueue(object : retrofit2.Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.message?.let { displayErrorDialog(it) }
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                newfeeds.removeAt(currentPos)
                adapterNewFeeds.notifyDataSetChanged()
            }
        })
    }

    private fun displayDeleteDialog(id: Int) {
        val errorDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        errorDialog.setTitle(
            getString(R.string.dialog_title_delete)
        )
            .setMessage(getString(R.string.dialog_message_delete))
            .setNegativeButton(R.string.dialog_text_cancel) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.dialog_text_ok) { _, _ -> deleteNewFeed(id) }
            .show()
    }

    private fun itemOnclick(adapter: ItemFeedAdapter) {
        adapter.click(object : ItemFeedAdapter.Onclick {
            override fun iconEditFeed(post: NewPost) {
                val id = post.id
                val content = post.content
                val image = post.image
                Bundle().let {
                    it.putString("token", token)
                    it.putInt("id", id)
                    it.putString("content", content)
                    it.putString("image", image)
                    val update = UpdateFeed.newInstance()
                    update.arguments = it
                    (activity as? RecyclerViewNewFeed)?.openFragment(
                        update, true
                    )
                }
                Log.d("TAG", "iconEditFeed: ${post.image}")
            }
        })
    }
}
