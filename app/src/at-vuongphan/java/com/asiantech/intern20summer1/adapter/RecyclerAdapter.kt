package com.asiantech.intern20summer1.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.ItemRecycler
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.item_layout.view.*

class RecyclerAdapter(private val mutableList: MutableList<ItemRecycler>) :
    RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {
    companion object {
        private const val BLACK_HEART_SYMBOL = "\uD83D\uDDA4"
    }

    internal var onItemClicked: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = mutableList.size
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var avatar: CircleImageView = itemView.circleImageAvatar
        private var nameAvatar: TextView = itemView.tvTitle
        private var userName: TextView = itemView.tvUserName
        private var image: ImageView = itemView.imgCenter
        private var iconHeart: ImageView = itemView.imgIconHeart
        private var information: TextView = itemView.tvInformation
        private var countLike: TextView = itemView.tvAmountHeart

        init {
            iconHeart.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
                iconHeart.playSoundEffect(SoundEffectConstants.CLICK)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindData() {
            mutableList[adapterPosition].let {
                val stName = it.title
                val countLikes = "$BLACK_HEART_SYMBOL ${it.amountHeart} likes"
                nameAvatar.text = stName
                val spannable = SpannableString(stName + " " + it.name)
                spannable.setSpan(
                    ForegroundColorSpan(Color.RED),
                    0,
                    stName.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                userName.text = spannable
                // titleName.text = stNameOne
                information.text = it.information
                countLike.text = countLikes
                if (it.statusHeart) {
                    iconHeart.setImageResource(R.drawable.ic_heart_red)
                } else {
                    iconHeart.setImageResource(R.drawable.ic_heart_transparent)
                }
                image.setImageResource(it.image)
                avatar.setImageResource(it.avatar)
            }
        }
    }
}
