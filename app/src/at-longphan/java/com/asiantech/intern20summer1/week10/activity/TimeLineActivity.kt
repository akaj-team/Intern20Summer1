package com.asiantech.intern20summer1.week10.activity

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.adapter.PostAdapter
import com.asiantech.intern20summer1.week10.api.RetrofitClient
import com.asiantech.intern20summer1.week10.model.Post
import com.asiantech.intern20summer1.week10.model.User
import com.asiantech.intern20summer1.week10.other.*
import kotlinx.android.synthetic.`at-longphan`.activity_time_line_w10.*
import retrofit2.Call
import retrofit2.Response

class TimeLineActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    /**
     * lay user tu share preference
     * add token vao header request api
     * call api
     */

    companion object {
        private const val TIME_DELAY: Long = 2000
        private const val ITEMS_INIT: Int = 25
        private const val ITEMS_TAKE: Int = 10
    }

    private lateinit var loginUser: User
    private var postItemsAll = listOf<Post>()
    private var postItemsShowed = mutableListOf<Post>()
    private lateinit var rvPostItems: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var adapter: PostAdapter? = null
    private var isLoading = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line_w10)
        configStatusBar()

        loginUser = getLoginUserData()

        initRecyclerView()
        initData()
        //initAdapter()

        //initIsLikedImageViewClickListener()
        //assignRecyclerView()
        //initSwipeRefreshLayout()
        //initScrollListener()
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        reloadData()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun initRecyclerView() {
        rvPostItems = findViewById(R.id.recycleViewTimeLineW10)
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipeContainerW10)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initData() {

        /*postItemsAll = TimeLineItem().createTimeLineItemsList(ITEMS_INIT)
        for (i in 0 until ITEMS_TAKE) {
            postItemsShowed.add(postItemsAll[i])
        }*/
        val callApi =
            RetrofitClient.createPostService()
                ?.getPosts(loginUser.token)

        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.progress_dialog_loading)
        val progressDialogLoading = builder.create()
        progressDialogLoading?.show()

        callApi?.enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                progressDialogLoading?.dismiss()
                Toast.makeText(
                    this@TimeLineActivity,
                    getString(R.string.text_no_network_conennection),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                progressDialogLoading?.dismiss()
                when (response.code()) {
                    200 -> {
                        postItemsAll = response.body()!!

                        for (i in 0 until ITEMS_TAKE) {
                            postItemsShowed.add(postItemsAll[i])
                        }

                        initAdapter()
                        initIsLikedImageViewClickListener()
                        assignRecyclerView()
                        initSwipeRefreshLayout()
                        initScrollListener()
                    }
                    else -> {
                        Toast.makeText(
                            this@TimeLineActivity,
                            getString(R.string.text_error_occurred),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = PostAdapter(this@TimeLineActivity, postItemsShowed)
    }

    private fun initIsLikedImageViewClickListener() {
        adapter?.onIsLikedImageViewClick = { position ->
            postItemsShowed[position].let {
                it.likeFlag = !it.likeFlag
                if (it.likeFlag) {
                    it.likeCount++
                } else {
                    if (it.likeCount > 0) {
                        it.likeCount--
                    }
                }
                if (it.likeCount > 1) {
                    it.isPluralLike = true
                }
            }
            adapter?.notifyItemChanged(position)
            // Remove flash animation when interact with a row item
            (rvPostItems.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
    }

    private fun assignRecyclerView() {
        rvPostItems.adapter = adapter
        rvPostItems.layoutManager = LinearLayoutManager(this)
    }

    private fun initScrollListener() {
        rvPostItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager?
                if (!isLoading) {
                    linearLayoutManager?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == postItemsShowed.size - 1
                            && postItemsShowed.size < postItemsAll.size
                        ) {
                            loadMore()
                            isLoading = true
                            progressBarW10.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun loadMore() {
        Handler().postDelayed({
            var currentSize = postItemsShowed.size
            val nextLimit = currentSize + 10
            while (currentSize < postItemsAll.size && currentSize < nextLimit) {
                postItemsShowed.add(postItemsAll[currentSize])
                currentSize++
            }
            adapter?.notifyDataSetChanged()
            isLoading = false
            progressBarW10.visibility = View.INVISIBLE
        }, TIME_DELAY)
    }

    private fun reloadData() {
        postItemsShowed.clear()
        for (i in 0 until ITEMS_TAKE) {
            postItemsShowed.add(postItemsAll[i])
        }
        adapter?.notifyDataSetChanged()
    }

    private fun getLoginUserData(): User {
        val sharePref: SharedPreferences =
            this.getSharedPreferences(USER_DATA_PREFS_WEEK_10, Context.MODE_PRIVATE)

        return User(
            sharePref.getInt(ID_KEY, -1),
            sharePref.getString(EMAIL_KEY, null),
            sharePref.getString(FULL_NAME_KEY, null),
            sharePref.getString(TOKEN_KEY, null)
        )
    }
}
