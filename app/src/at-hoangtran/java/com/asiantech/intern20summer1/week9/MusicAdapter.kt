package com.asiantech.intern20summer1.week9

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.song_item_recycler.view.*

class MusicAdapter(private val songList: MutableList<Song>) :
    RecyclerView.Adapter<MusicAdapter.ItemViewHolder>() {

    internal var onClicked: (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.song_item_recycler, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: MusicAdapter.ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var duration: TextView = itemView.tvSongDuration
        private var songName: TextView = itemView.tvSongName
        private var songAuthor: TextView = itemView.tvSongAuthor
        private var imageSong: ImageView = itemView.imgPlayPause

        fun bind(position: Int) {
            val song = songList[position]
            songName.text = song.name
            duration.text = getDuration(itemView, song.duration)
            songAuthor.text = song.author
            val bitmap = convertToBitmap(itemView.context, Uri.parse(song.imgUri))
            if (bitmap != null) {
                imageSong.setImageBitmap(bitmap)
            } else {
                imageSong.setImageResource(R.mipmap.ic_launcher_round)
            }
            itemView.lnItem.setOnClickListener {
                onClicked(position)
            }
        }
    }

    private fun convertToBitmap(context: Context, path: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(MusicPlayerActivity(), path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    private fun getDuration(itemView: View, duration: Int): String {
        val sec = duration / 1000 % 60
        val min = ((duration - sec) / 1000 / 60).toLong()
        return String.format("%02d:%02d", min, sec)
    }
}
