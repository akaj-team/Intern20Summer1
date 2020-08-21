package com.asiantech.intern20summer1.adapter.w9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.w9.Music
import com.asiantech.intern20summer1.data.w9.MusicData
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.`at-vuongphan`.item_recycler_music.view.*

class MusicAdapter(private val recyclerViewHolder: MutableList<Music>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var onSongClicked: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_music, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = recyclerViewHolder.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bindData()
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvNameMusic: TextView = itemView.tvNameMusic
        private var tvTime: TextView = itemView.tvTime
        private var tvArtist: TextView = itemView.tvArtist
        private var imgMusic: CircleImageView = itemView.imgMusic

        init {
            itemView.setOnClickListener {
                onSongClicked.invoke(adapterPosition)
            }
        }

        internal fun bindData(){
            recyclerViewHolder[adapterPosition].let {
                tvNameMusic.text = it.name
                tvTime.text = MusicData.toMin(it.duration.toLong(),itemView.context)
                tvArtist.text = it.artist
                Glide.with(itemView.context)
                    .load(it.image)
                    .placeholder(R.drawable.ic_background)
                    .into(imgMusic)
            }
        }
    }
}