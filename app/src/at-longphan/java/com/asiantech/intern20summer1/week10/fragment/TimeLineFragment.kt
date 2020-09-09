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
import com.asiantech.intern20summer1.week10.model.ToggleLikeResponse
import com.asiantech.intern20summer1.week10.model.User
import com.asiantech.intern20summer1.week10.other.*
import kotlinx.android.synthetic.`at-longphan`.fragment_time_line_w10.*
import retrofit2.Call
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class TimeLineFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        internal fun newInstance() = TimeLineFragment()
        private const val TIME_DELAY: Long = 2000
        private const val ITEMS_TAKE: Int = 10
    }

    private lateinit var loginUser: User
    private var postItemsAll = listOf<Post>()
    private var postItemsShowed = mutableListOf<Post>()
    private var adapter: PostAdapter? = null
    private var isLoading = false
    private lateinit var progressDialogLoading: AlertDialog

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

        val progressDialogLoadingBuilder =
            AlertDialog.Builder(context).setView(R.layout.progress_dialog_loading)
        progressDialogLoading = progressDialogLoadingBuilder.create()

        text_view_fragment_time_line_title_w10?.text = loginUser.fullName

        initData()
        initListeners()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onRefresh() {
        swipeContainerW10?.isRefreshing = true
        initData()
        swipeContainerW10?.isRefreshing = false
    }

    private fun initSwipeRefreshLayout() {
        swipeContainerW10?.setOnRefreshListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initData() {
        postItemsShowed.clear()

        val getPosts = RetrofitClient.createPostService()?.getPosts(loginUser.token)

        progressDialogLoading.show()

        getPosts?.enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                progressDialogLoading.dismiss()
                Toast.makeText(
                    context,
                    getString(R.string.text_no_network_connection),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                progressDialogLoading.dismiss()
                when (response.code()) {
                    HttpsURLConnection.HTTP_OK -> {
                        postItemsAll = response.body()!!

                        for (i in 0 until ITEMS_TAKE) {
                            postItemsShowed.add(postItemsAll[i])
                        }

                        initAdapter()
                        initIsLikedImageViewClickListener()
                        initPostOptionImageViewClickListener()
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initIsLikedImageViewClickListener() {
        adapter?.onIsLikedImageViewClick = { position ->
            postItemsShowed[position].let {

                val toggleLikePost =
                    RetrofitClient.createPostService()?.toggleLikeFlag(loginUser.token, it.id)
                progressDialogLoading.show()

                toggleLikePost?.enqueue(object : retrofit2.Callback<ToggleLikeResponse> {
                    override fun onFailure(call: Call<ToggleLikeResponse>, t: Throwable) {
                        progressDialogLoading.dismiss()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.text_error_occurred),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onResponse(
                        call: Call<ToggleLikeResponse>,
                        response: Response<ToggleLikeResponse>
                    ) {
                        progressDialogLoading.dismiss()
                        when (response.code()) {
                            HttpsURLConnection.HTTP_OK -> {
                                val toggleLikeResponse = response.body()

                                it.likeFlag = toggleLikeResponse?.likeFlag!!
                                it.likeCount = toggleLikeResponse.likeCount
                                if (it.likeCount > 1) {
                                    it.isPluralLike = true
                                }
                                adapter?.notifyItemChanged(position)
                                (recycleViewTimeLineW10?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                                    false
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
        }
    }

    private fun initPostOptionImageViewClickListener() {
        adapter?.onPostOptionImageViewClick =
            { idPost: Int, image: String, content: String ->
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setTitle(getString(R.string.title_post_option_dialog))
                val optionList = arrayOf(
                    getString(R.string.edit_post_option),
                    getString(R.string.delete_post_option)
                )
                dialogBuilder.setItems(optionList) { _, which ->
                    when (which) {
                        0 -> {
                            addFragmentNewPostForEdit(idPost, image, content)
                        }
                        1 -> {
                            Toast.makeText(
                                context,
                                getString(R.string.text_open_soon),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                val dialog = dialogBuilder.create()
                dialog.show()
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
        if (postItemsShowed.size != 0) {
            Handler().postDelayed({
                var currentSize = postItemsShowed.size
                val nextLimit = currentSize + ITEMS_TAKE
                while (currentSize < postItemsAll.size && currentSize < nextLimit) {
                    postItemsShowed.add(postItemsAll[currentSize])
                    currentSize++
                }
                adapter?.notifyDataSetChanged()

                isLoading = false
                progressBarW10?.visibility = View.INVISIBLE
            }, TIME_DELAY)

            isLoading = true
            progressBarW10?.visibility = View.VISIBLE
        }
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
            ?.replace(R.id.frameLayoutActivityHomeW10, NewPostFragment.newInstance(loginUser.token))
            ?.addToBackStack(null)?.commit()
    }

    private fun addFragmentNewPostForEdit(idPost: Int, image: String, content: String) {
        fragmentManager?.beginTransaction()
            ?.replace(
                R.id.frameLayoutActivityHomeW10,
                NewPostFragment.newInstance(loginUser.token, idPost, image, content)
            )
            ?.addToBackStack(null)?.commit()
    }
}
