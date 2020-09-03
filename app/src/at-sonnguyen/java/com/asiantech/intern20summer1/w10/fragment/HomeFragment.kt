package com.asiantech.intern20summer1.w10.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.HomeActivity
import com.asiantech.intern20summer1.w10.adapter.RecyclerViewAdapter
import com.asiantech.intern20summer1.w10.data.Post
import kotlinx.android.synthetic.`at-sonnguyen`.w10_fragment_home.*

class HomeFragment : Fragment() {
    private var posts = mutableListOf<Post>()
    private var postAdapter = RecyclerViewAdapter(posts)
    private var isLoading = false
    private var currentPosition = -1

    companion object {
        private const val DELAY_TIME = 2000
        internal fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d("TAG000", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.w10_fragment_home, container, false)
        val toolbar = view?.findViewById<Toolbar>(R.id.toolBarNewFeed)

        (activity as HomeActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as HomeActivity).supportActionBar?.setIcon(R.drawable.ic_comment)
        Log.d("TAG000", "onCreateView: ")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG000", "onViewCreated: ")
//        getAllPost()
        initAdapter()
    }

//    private fun getAllPost() {
//        val service = APIClient.createServiceClient()?.create(PostAPI::class.java)
//        Log.d("TAG000", "getAllPost: 1")
//        val call = service?.getAllPost()
//        Log.d("TAG000", "getAllPost: 2")
//        call?.enqueue(object : Callback<MutableList<Post>> {
//            override fun onResponse(
//                call: Call<MutableList<Post>>,
//                response: Response<MutableList<Post>>
//            ) {
//                Toast.makeText(requireContext(), "load success", Toast.LENGTH_SHORT).show()
//                Log.d("TAG000", "onResponse: load success")
//            }
//
//            override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
//                t.message?.let {
//                    displayErrorDialog(it)
//                    Log.d("TAG000", "onFailure: fail")
//                }
//            }
//
//        })
//    }

    private fun initAdapter() {
        recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewHome.adapter = postAdapter
        postAdapter.onItemClicked = {
            if (posts[it].like_flag) {
                posts[it].like_count--
                posts[it].like_flag = !posts[it].like_flag
            } else {
                posts[it].like_count++
                posts[it].like_flag = !posts[it].like_flag
            }
//            handleHeartListener(posts[it].id,posts[it])
            postAdapter.notifyItemChanged(it,null)
            (recyclerViewHome?.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
    }

//    private fun handleHeartListener(id: Int, post: Post) {
//        val service = APIClient.createServiceClient()?.create(PostAPI::class.java)
//        val call = service?.updatePost(id, post)
//        call?.enqueue(object : Callback<Post> {
//            override fun onResponse(call: Call<Post>, response: Response<Post>) {}
//
//            override fun onFailure(call: Call<Post>, t: Throwable) {
//                t.message?.let {
//                    displayErrorDialog(it)
//                }
//            }
//
//        })
//    }

    private fun displayErrorDialog(message: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(R.string.w10_error_dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.w10_ok_button_text)) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
