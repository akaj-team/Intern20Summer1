package com.asiantech.intern20summer1.week10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-hoangtran`.loading_layout.view.*
import kotlinx.android.synthetic.`at-hoangtran`.post_item.view.*

class PostViewHolder(
    internal var postItems: MutableList<Post?>,
    internal val userId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE = 1
        private const val LOADING_TYPE = 0
    }

    internal var onHeartClicked: (Int) -> Unit = {}
    internal var onUpdateClicked: (Int) -> Unit = {}
    private val imageUrl = "https://at-a-trainning.000webhostapp.com/images/"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.post_item, parent, false)
            PostViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.loading_layout, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount() = postItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder) {
            holder.onBindData(position)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemViewType(position: Int) =
        if (postItems[position] == null) LOADING_TYPE else ITEM_TYPE

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.progressBar
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBindData(position: Int) {
            val postItem = postItems[position]
            val tvContent = itemView.tvStatus
            val imgPost = itemView.imgTimeLine
            val imgLike = itemView.imgLike
            val tvCountLike = itemView.tvCountLike
            val imgDelete = itemView.imgDelete
            val imgUpdate = itemView.imgUpdate

            postItem?.let {
                Glide.with(itemView).load(imageUrl + it.image).into(imgPost)
                tvContent.text = it.content
                tvCountLike.text = it.likeCount.toString()
                if (userId == it.userId) {
                    imgDelete.visibility = View.VISIBLE
                    imgUpdate.visibility = View.VISIBLE
                } else {
                    imgDelete.visibility = View.INVISIBLE
                    imgUpdate.visibility = View.INVISIBLE
                }
                if (it.likeFlag) {
                    imgLike.setImageResource(R.mipmap.ic_launcher_round)
                } else {
                    imgLike.setImageResource(R.mipmap.ic_launcher_round)
                }

                imgLike.setOnClickListener {
                    onHeartClicked(position)
                }

                imgUpdate.setOnClickListener {
                    onUpdateClicked(position)
                }
            }
        }
    }
}
