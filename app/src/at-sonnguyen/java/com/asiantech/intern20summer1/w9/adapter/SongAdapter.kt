package com.asiantech.intern20summer1.w9.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData
import com.bumptech.glide.Glide

class SongAdapter(private val recyclerViewHolder: MutableList<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var onItemClicked : (position : Int) ->Unit =  {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.w9_item_line, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.binData()
    }

    override fun getItemCount() = recyclerViewHolder.size
    inner class RecyclerViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private var imgSong : ImageView = itemView.findViewById(R.id.imgSong)
        private var tvSongName : TextView = itemView.findViewById(R.id.tvSongName)
        private var tvSingerName : TextView = itemView.findViewById(R.id.tvSingerName)
        private var tvDuration : TextView = itemView.findViewById(R.id.tvDuration)

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        internal fun binData(){
            recyclerViewHolder[adapterPosition].let {
                Glide.with(itemView.context)
                    .load(it.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imgSong)
                tvSongName.text = it.songName
                tvSingerName.text=it.singerName
                tvDuration.text = SongData.toMin(it.duration.toLong(),itemView.context)
            }
        }
    }
}
