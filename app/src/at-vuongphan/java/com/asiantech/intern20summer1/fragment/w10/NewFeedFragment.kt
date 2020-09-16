@file:Suppress("DEPRECATION")

package com.asiantech.intern20summer1.fragment.w10

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.RecyclerViewNewFeed
import com.asiantech.intern20summer1.adapter.w10.ItemFeedAdapter
import com.asiantech.intern20summer1.api.w10.repository.PostRepository
import com.asiantech.intern20summer1.model.w10.NewPost
import com.asiantech.intern20summer1.model.w10.ResponseLike
import com.asiantech.intern20summer1.views.PostViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_new_feed.*
import retrofit2.Response

class NewFeedFragment : Fragment() {
    private var newfeeds = mutableListOf<NewPost>()
    private var adapterNewFeeds = ItemFeedAdapter(newfeeds)
    private var isLoading = false
    private var currentPos = -1

    internal var token: String? = null
    private var viewModel: PostViewModel? = null

    companion object {
        private const val DELAY_TIME = 2000L
        internal fun newInstance() = NewFeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = PostViewModel(PostRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_fragment_new_feed, container, false)
        val toolbar = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.tbNewFeed)
        (activity as RecyclerViewNewFeed).setSupportActionBar(toolbar)
        getToken()
        val imgSearch = view.findViewById<ImageView>(R.id.imgSearch)
        imgSearch?.setOnClickListener {
            fragmentManager?.let { it1 -> FragmentDialogSearch().show(it1, "") }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        getListAPI()
    }

    private fun initAdapter() {
        recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewMain.adapter = adapterNewFeeds
        adapterNewFeeds.onItemClicked = { position ->
            addLikePost(position)
        }
        adapterNewFeeds.onItemDeleteClicked = {
            currentPos = it
        }
    }

    private fun getToken() {
        val bundle = arguments
        if (bundle != null) {
            token = bundle.getString(resources.getString(R.string.key_data)).toString()
            Log.d("TAG", "getToken: $token")
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.string_error),
                Toast.LENGTH_SHORT
            ).show()
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

    @Suppress("NAME_SHADOWING")
    private fun getListAPI() {
        token?.let { viewModel?.getPost(it) }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ it: Response<MutableList<NewPost>> ->
                if (it.isSuccessful) {
                    it.body().let {
                        it?.let { it1 -> newfeeds.addAll(it1) }
                    }
                    progressLoadData.visibility = View.GONE
                    initAdapter()
                } else {
                    Toast.makeText(requireContext(), "Thất bại", Toast.LENGTH_SHORT).show()
                }
            }, {
                it.message?.let { it -> displayErrorDialog(it) }
            })
    }

    private fun displayErrorDialog(message: String) {
        val errorDialog = AlertDialog.Builder(requireContext())
        errorDialog.setTitle(
            getString(R.string.dialog_title_error)
        )
            .setMessage(message)
            .setPositiveButton(R.string.dialog_text_ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun addLikePost(position: Int) {
        token?.let { viewModel?.likePost(it, newfeeds[position].id) }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ it: Response<ResponseLike> ->
                if (it.isSuccessful) {
                    it.body()?.let {
                        newfeeds[position].like_count = it.likeCount
                        newfeeds[position].like_flag = it.like_flag
                        adapterNewFeeds.notifyItemChanged(position, null)
                        (recyclerViewMain.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                            true
                    }
                }
            }, {
                it.message.let { it -> it?.let { it1 -> displayErrorDialog(it1) } }
            })
    }
}
