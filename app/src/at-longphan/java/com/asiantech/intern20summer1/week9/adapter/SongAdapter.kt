package com.asiantech.intern20summer1.week9.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.model.Song
import com.asiantech.intern20summer1.week9.model.Units
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SongAdapter(internal val songs: ArrayList<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    companion object {
        internal const val SONG_LIST_PATH = "song_list_path"
        internal const val SONG_ITEM_POSITION = "song_item_position"
    }

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        (holder as? SongViewHolder)?.bindData()
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val songNameTextView: TextView = itemView.findViewById(R.id.tvSongTitle)
        private val songDurationTextView: TextView = itemView.findViewById(R.id.tvSongDuration)
        private val songArtistTextView: TextView = itemView.findViewById(R.id.tvSongArtist)
        private val songImageImageView: ImageView = itemView.findViewById(R.id.imgSongImage)

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(adapterPosition)
            }
        }

        internal fun bindData() {
            songs[adapterPosition].let {
                val requestOption = RequestOptions().placeholder(R.drawable.ic_music_note_60)
                songNameTextView.text = it.name
                songDurationTextView.text = Units.convertTimeMusic(it.duration)
                songArtistTextView.text = it.artist
                Glide.with(itemView)
                    .setDefaultRequestOptions(requestOption)
                    .load(Uri.parse(it.image))
                    .into(songImageImageView)
            }
        }
    }
}
