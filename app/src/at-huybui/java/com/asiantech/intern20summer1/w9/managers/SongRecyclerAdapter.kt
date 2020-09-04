package com.asiantech.intern20summer1.w9.managers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    internal var onItemClick: (Int) -> Unit = {}

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
        private var iconPlayer = itemView.imgDvd
        private var nameSong = itemView.tvNameSong
        private var nameSinger = itemView.tvNameSinger
        private var lengthSong = itemView.tvLengthSong

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bindData() {
            songList[adapterPosition].let { song ->
                nameSong.isSelected = true
                nameSong.text = song.nameSong
                nameSinger.text = song.singer
                lengthSong.text = song.duration
                iconPlayer.setImageBitmap(song.getPicture(itemView.context))
            }
        }
    }
}
