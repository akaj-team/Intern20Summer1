package com.asiantech.intern20summer1.w9.managers

import android.util.Log.d
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
        private var iconPlayer: ImageView = itemView.btnPlayItem
        private var nameSong: TextView = itemView.tvNameSongItem
        private var nameSinger: TextView = itemView.tvNameSingerItem
        private var lengthSong: TextView = itemView.tvLengthSongItem


        init {
            itemView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bindData() {
            songList[adapterPosition].let { song ->
                val songNew = Song().getData(itemView.context, song)
                nameSong.isSelected = true
                nameSong.text = songNew.nameSong
                nameSinger.text = songNew.singer
                lengthSong.text = songNew.duration
                val bitmap = Song().getPicture(itemView.context, song)
                if (bitmap == null) {
                    iconPlayer.setImageResource(R.drawable.ic_dvd_player)
                } else {
                    iconPlayer.setImageBitmap(bitmap)
                }
                d("XXX", "[adapter]" + songNew.contentUri)
            }
        }
    }
}
