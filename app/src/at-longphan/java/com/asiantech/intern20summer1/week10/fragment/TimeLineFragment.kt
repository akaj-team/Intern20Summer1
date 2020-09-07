package com.asiantech.intern20summer1.week10.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.`at-longphan`.fragment_time_line_w10.*
import retrofit2.Call
import retrofit2.Response

class TimeLineFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        internal fun newInstance() = TimeLineFragment()
        private const val TIME_DELAY: Long = 2000
        private const val ITEMS_TAKE: Int = 10
    }

    private lateinit var loginUser: User
    private var postItemsAll = listOf<Post>()
    private var postItemsShowed = mutableListOf<Post>()
    private lateinit var rvPostItems: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var adapter: PostAdapter? = null
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_time_line_w10, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginUser = getLoginUserData()
        text_view_fragment_time_line_title_w10?.text = loginUser.fullName
        //initRecyclerView()
        initData()
        initListeners()
    }

    override fun onRefresh() {
        swipeContainerW10?.isRefreshing = true
        reloadData()
        swipeContainerW10?.isRefreshing = false
    }

    /*private fun initRecyclerView() {
        rvPostItems = activity?.findViewById(R.id.recycleViewTimeLineW10)!!
    }*/

    private fun initSwipeRefreshLayout() {
        //swipeRefreshLayout = activity?.findViewById(R.id.swipeContainerW10)!!
        swipeContainerW10?.setOnRefreshListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initData() {

        val callApi =
            RetrofitClient.createPostService()
                ?.getPosts(loginUser.token)

        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.progress_dialog_loading)
        val progressDialogLoading = builder.create()
        progressDialogLoading?.show()

        callApi?.enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                progressDialogLoading?.dismiss()
                Toast.makeText(
                    context,
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

                        Toast.makeText(
                            context,
                            "getString(R.string.text_error_occurred)",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        initAdapter()
                        initIsLikedImageViewClickListener()
                        assignRecyclerView()
                        initSwipeRefreshLayout()
                        initScrollListener()
                    }
                    else -> {
                        Toast.makeText(
                            context,
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
        adapter = context?.let { PostAdapter(it, postItemsShowed) }
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
            (recycleViewTimeLineW10?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                false
        }
    }

    private fun assignRecyclerView() {
        recycleViewTimeLineW10?.adapter = adapter
        recycleViewTimeLineW10?.layoutManager = LinearLayoutManager(context)
    }

    private fun initScrollListener() {
        recycleViewTimeLineW10?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                            progressBarW10?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun initListeners() {
        imgCreateNewPostW10?.setOnClickListener {
            addFragmentNewPost()
        }
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
            progressBarW10?.visibility = View.INVISIBLE
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
            context?.getSharedPreferences(USER_DATA_PREFS_WEEK_10, Context.MODE_PRIVATE)!!

        return User(
            sharePref.getInt(ID_KEY, -1),
            sharePref.getString(EMAIL_KEY, null),
            sharePref.getString(FULL_NAME_KEY, null),
            sharePref.getString(TOKEN_KEY, null)
        )
    }

    private fun addFragmentNewPost() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.frameLayoutActivityHomeW10, NewPostFragment())
            //?.hide(this)
            ?.addToBackStack(null)?.commit()
    }
}