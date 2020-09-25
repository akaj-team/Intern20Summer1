package com.asiantech.intern20summer1.week12.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.utils.UtilsConvert
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.item_new_feed.view.*

class ItemFeedAdapter(internal val newFeeds: MutableList<NewPost?>?) :
    RecyclerView.Adapter<ItemFeedAdapter.NewFeedViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}
    internal var onItemDeleteClicked: (position: Int) -> Unit = {}
    private var url = "https://at-a-trainning.000webhostapp.com/images/"
    var onClick: Onclick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFeedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_new_feed, parent, false)
        return NewFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewFeedViewHolder, position: Int) {
        (holder as? NewFeedViewHolder)?.bindData()
    }

    override fun getItemCount(): Int = newFeeds?.size!!

    inner class NewFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.tvName
        private val imgAvatar: CircleImageView = itemView.imgAvatar
        private val imgMain: ImageView = itemView.imgMainPhoto
        private val imgHeart: ImageView = itemView.imgHeart
        private val tvLike: TextView = itemView.tvLike
        private val tvStatus: TextView = itemView.tvStatus
        private val tvNameStatus: TextView = itemView.tvNameStatus
        private val imgDelete: ImageView = itemView.imgOption
        private val imgUpdate: ImageView = itemView.imgUpdate

        init {
            imgHeart.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
            imgDelete.setOnClickListener {
                onItemDeleteClicked.invoke(adapterPosition)
            }
        }

        fun bindData() {
            val post = newFeeds?.get(adapterPosition)
            newFeeds?.get(adapterPosition)?.let {
                val image = url.plus(it.image)
                tvName.text = it.content
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.tran_thi_duyen)
                Glide.with(itemView)
                    .load(image)
                    .apply(options)
                    .into(imgMain)
                Glide.with(itemView)
                    .load(image)
                    .apply(options)
                    .into(imgAvatar)
                if (it.like_flag) imgHeart.setImageResource(R.drawable.ic_hearted) else imgHeart.setImageResource(
                    R.drawable.ic_heart
                )
                tvStatus.text = it.content
                tvNameStatus.text = UtilsConvert().convertDate(it.created_at)
                tvLike.text =
                    itemView.context.getString(R.string.text_view_text_like_number, it.like_count)
            }
            imgUpdate.setOnClickListener {
                post?.let { it1 -> onClick?.iconEditFeed(it1) }
            }
        }
    }

    fun click(onClick: Onclick) {
        this.onClick = onClick
    }

    interface Onclick {
        fun iconEditFeed(post: NewPost)
    }
}
