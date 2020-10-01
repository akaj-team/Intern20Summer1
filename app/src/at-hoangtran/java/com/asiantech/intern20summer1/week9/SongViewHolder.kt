package com.asiantech.intern20summer1.week9

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.Utils.convertToBitmap
import kotlinx.android.synthetic.`at-hoangtran`.item_song.view.*

class SongViewHolder(private var songList: MutableList<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var onClicked: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SongViewHolder) {
            holder.onBind(position)
        }
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvName = itemView.tvSongName
        private var tvArtist = itemView.tvArtist
        private var tvDuration = itemView.tvDuration
        private var imgSong = itemView.imgPlay

        fun onBind(position: Int) {
            val song = songList[position]
            tvName.text = song.name
            tvArtist.text = song.artist
            tvDuration.text = getDuration(song.duration)
            val bitmap = convertToBitmap(itemView.context, Uri.parse(song.image))
            if (bitmap != null) {
                imgSong.setImageBitmap(bitmap)
            } else {
                imgSong.setImageResource(R.drawable.ic_launcher_background)
            }

            itemView.clMusicContainer.setOnClickListener {
                onClicked(position)
            }
        }
    }

    fun getDuration(duration: Int): String {
        val second = duration / 1000 % 60
        val minute = ((duration - second) / 1000 / 60).toLong()
        return String.format("%02d: %02d", minute, second)
    }
}