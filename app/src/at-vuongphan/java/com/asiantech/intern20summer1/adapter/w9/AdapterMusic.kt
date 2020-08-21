package com.asiantech.intern20summer1.adapter.w9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.w9.ItemMusic
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.item_recycler_music.view.*

class AdapterMusic(private val mutableList: MutableList<ItemMusic>) :
    RecyclerView.Adapter<AdapterMusic.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMusic.ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_music, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData()
    }

    override fun getItemCount() = mutableList.size
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: CircleImageView = itemView.imgMusic
        private var nameSong: TextView = itemView.tvNameMusic
        private var nameArtist: TextView = itemView.tvArtist

        fun bindData() {
            mutableList[adapterPosition].let {
                it.image?.let { it1 -> image.setImageResource(it1) }
                it.nameSong.let { it2 -> nameSong.text = it2 }
                it.nameArtist.let { it3 -> nameArtist.text = it3 }
            }
        }
    }
}