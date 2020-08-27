package com.asiantech.intern20summer1.week9.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.extensions.Utils
import com.asiantech.intern20summer1.week9.models.Song
import kotlinx.android.synthetic.`at-linhle`.item_list_song.view.*

class SongViewHolder(private val songItems: MutableList<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        internal const val ONE_THOUSAND = 1000
        internal const val SIXTY = 60
    }

    internal var onClicked: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = songItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SongViewHolder) {
            holder.onBindData(position)
        }
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSongName: TextView = itemView.tvSongName
        private val tvArtist: TextView = itemView.tvArtist
        private val tvDuration: TextView = itemView.tvDuration
        private val imgSong: ImageView = itemView.imgPlay

        fun onBindData(position: Int) {
            val song = songItems[position]
            tvSongName.text = song.songName
            tvArtist.text = song.artist
            tvDuration.text = getDuration(itemView,song.duration)
            val bitmap = Utils.convertToBitmap(itemView.context, Uri.parse(song.imgUri))
            if (bitmap != null) {
                imgSong.setImageBitmap(bitmap)
            } else {
                imgSong.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
            }

            itemView.clMusicContainer.setOnClickListener {
                onClicked(position)
            }
        }
    }

    fun getDuration(itemView: View, duration: Int): String {
        val seconds = duration / ONE_THOUSAND % SIXTY
        val minutes = ((duration - seconds) / ONE_THOUSAND / SIXTY).toLong()
        return String.format(itemView.context.getString(R.string.duration_description), minutes, seconds)
    }
}
