package com.asiantech.intern20summer1.week9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.song_item_recycler.view.*

class MusicAdapter(private val songList: MutableList<Song>) :
    RecyclerView.Adapter<MusicAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.song_item_recycler, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: MusicAdapter.ItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var duration: TextView = itemView.tvSongDuration
        private var songName: TextView = itemView.tvSongName
        private var songAuthor: TextView = itemView.tvSongAuthor

        fun bind() {
            songList[adapterPosition].let {
                songName.text = it.name
                songAuthor.text = it.author
                duration.text = it.duration
            }
        }
    }
}
