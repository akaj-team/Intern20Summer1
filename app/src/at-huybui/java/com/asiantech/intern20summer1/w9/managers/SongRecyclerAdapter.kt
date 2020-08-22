package com.asiantech.intern20summer1.w9.managers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.models.Song
import kotlinx.android.synthetic.`at-huybui`.w9_item_recycler_song.view.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is adapter class for musics recycler view
 */

class SongRecyclerAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<SongRecyclerAdapter.ItemViewHolder>() {

    internal var onPlayerClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.w9_item_recycler_song, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var iconPlayer: ImageView = itemView.btnPlayItem
        private var nameSong: TextView = itemView.tvNameSongItem
        private var nameSinger: TextView = itemView.tvNameSingerItem
        private var lengthSong: TextView = itemView.tvLengthSongItem

        init {
            iconPlayer.setOnClickListener {
                onPlayerClick.invoke(adapterPosition)
            }
        }

        fun bindData() {
            songList[adapterPosition].let { song ->
                nameSong.text = song.nameSong
                nameSinger.text = song.nameSinger
                lengthSong.text = song.lengthSong
            }
        }
    }
}
