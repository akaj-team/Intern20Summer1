package com.asiantech.intern20summer1.week10.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.models.Post
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-linhle`.item_list_time_line.view.*
import kotlinx.android.synthetic.`at-linhle`.loading_layout.view.*

class PostViewHolder(
    internal var postItems: MutableList<Post?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_ITEM_TYPE = 1
        private const val VIEW_LOADING_TYPE = 0
    }

    internal var onHeartClicked: (Int) -> Unit = {}
    private val imageUrl = "https://at-a-trainning.000webhostapp.com/images/"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM_TYPE) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_time_line, parent, false)
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
        if (postItems[position] == null) VIEW_LOADING_TYPE else VIEW_ITEM_TYPE

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

            postItem?.let {
                Glide.with(itemView).load(imageUrl + it.image).into(imgPost)
                tvContent.text = it.content
                tvCountLike.text = it.likeCount.toString()
                if (it.likeFlag) {
                    imgLike.setImageResource(R.drawable.ic_heart_bold_active)
                } else {
                    imgLike.setImageResource(R.drawable.ic_heart_bold)
                }

                imgLike.setOnClickListener {
                    onHeartClicked(position)
                }
            }
        }
    }
}
