package com.asiantech.intern20summer1.w10.fragment

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import com.asiantech.intern20summer1.w10.adapter.RecyclerAdapter
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiPostService
import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.models.PostItem
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_home.*
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    companion object {
        private const val IMAGE_ADD =
            """https://kynguyenlamdep.com/wp-content/uploads/2020/01/hinh-anh-dep-hoa-bo-cong-anh.jpg"""
        private const val CONTENT =
            """ahihi anh huy pro vô đối đẹp trai hết sức tưởng tượng luôn á"""
        private const val CREATED = "12:45"
        private const val KEY_PUT_ACCOUNT = "key_put_account"
        internal fun newInstance(account: Account) = HomeFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_PUT_ACCOUNT, account)
            }
        }
    }

    private var callApi: ApiPostService? = null
    private var account = Account()
    var postLists = mutableListOf<PostItem>()
    var postAdapter = RecyclerAdapter(postLists)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callApi = Api().newInstance()?.create(ApiPostService::class.java)
        account = arguments?.getSerializable(KEY_PUT_ACCOUNT) as Account
        d("homeFragment", account.toString())
        return inflater.inflate(R.layout.w10_fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initRecyclerView()
        initListener()
    }

    private fun initRecyclerView() {
        initData()
        initAdapter()
    }

    private fun initListener() {
        imgAddPost_Home_w10?.setOnClickListener {
            (activity as ApiMainActivity).handleShowDialogFragment()
        }
    }

    private fun initData() {
        callApi?.getPostLists(account.token)?.enqueue(object : retrofit2.Callback<List<PostItem>> {
            override fun onResponse(
                call: Call<List<PostItem>>,
                response: Response<List<PostItem>>
            ) {
                response.body()?.toCollection(postLists)
                d("homeFragment",postLists.toString())
                postAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<PostItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initAdapter() {

        postAdapter.onItemClicked = { position ->
            postLists[position].let {
                it.like_flag = !it.like_flag
                if (it.like_flag) {
                    it.like_count++
                } else {
                    it.like_count--
                }
            }

            postAdapter.notifyItemChanged(position, null)
            (recyclerView_Home_w10.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
                false
        }

        recyclerView_Home_w10.adapter = postAdapter
        recyclerView_Home_w10.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_Home_w10.setHasFixedSize(true)
    }

}
