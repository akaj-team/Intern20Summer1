package com.asiantech.intern20summer1.fragment.w10

import android.app.AlertDialog
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
import com.asiantech.intern20summer1.activity.w10.RecyclerViewNewFeed
import com.asiantech.intern20summer1.adapter.ItemFeedAdapter
import com.asiantech.intern20summer1.api.ClientAPI
import com.asiantech.intern20summer1.api.PostAPI
import com.asiantech.intern20summer1.model.NewPost
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_new_feed.*
import retrofit2.Call
import retrofit2.Response

class NewFeedFragment : Fragment() {
    private var newfeeds = mutableListOf<NewPost>()
    private var adapterNewFeeds = ItemFeedAdapter(newfeeds)
    private var isLoading = false
    private val delayTime: Long = 2000
    private var currentPos = -1
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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListAPI()
        initListener()
        initImageViewPlus()
    }

    private fun initImageViewPlus() {
        imgPlus?.setOnClickListener {
            (activity as? RecyclerViewNewFeed)?.openFragment(AddNewFeedFragment())
        }
    }

    private fun initAdapter() {
        recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewMain.adapter = adapterNewFeeds
        adapterNewFeeds.onItemClicked = {
            val isHeart = newfeeds[it].like_flag
            if (isHeart) {
                newfeeds[it].like_flag = !isHeart
                newfeeds[it].like_count--
            } else {
                newfeeds[it].like_flag = !isHeart
                newfeeds[it].like_count++
            }
            initHeart(newfeeds[it].id, newfeeds[it])
            adapterNewFeeds.notifyItemChanged(it, null)
            (recyclerViewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        adapterNewFeeds.onItemDeleteClicked = {
            displayDeleteDialog(newfeeds[it].id)
            currentPos = it
        }
        //itemOnclick(adapterNewFeeds)
    }

    private fun initListener() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                newfeeds.clear()
                getListAPI()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, delayTime)
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
                        }, delayTime)
                    }
                }
            }
        })
    }

    private fun initHeart(id: Int, newFeed: NewPost) {
        val service = ClientAPI.createServiceClient()?.create(PostAPI::class.java)
        val call = service?.updateNewPost(id, newFeed)
        call?.enqueue(object : retrofit2.Callback<NewPost> {
            override fun onFailure(call: Call<NewPost>, t: Throwable) {
                t.message?.let { displayErrorDialog(it) }
            }

            override fun onResponse(call: Call<NewPost>, response: Response<NewPost>) {
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
        val service = ClientAPI.createServiceClient()?.create(PostAPI::class.java)
        val call = service?.getAllPost()
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
        val service = ClientAPI.createServiceClient()?.create(PostAPI::class.java)
        val call = service?.deleteNewPost(id)
        call?.enqueue(object : retrofit2.Callback<NewPost> {
            override fun onFailure(call: Call<NewPost>, t: Throwable) {
                t.message?.let { displayErrorDialog(it) }
            }

            override fun onResponse(call: Call<NewPost>, response: Response<NewPost>) {
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
}
