package com.asiantech.intern20summer1.w9.managers

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
                val songNew = getData(itemView.context, song)
                nameSong.text = songNew.nameSong
                nameSinger.text = songNew.singer
                lengthSong.text = songNew.duration
                getPicture(itemView.context, song)?.let {
                    iconPlayer.setImageBitmap(it)
                }
            }
        }
    }

    private fun getData(context: Context, song: Song): Song {
        var songNew = song
        MediaMetadataRetriever().apply {
            var name = "no name"
            var duration = "00:00"
            var author = "no name"
            setDataSource(context, Uri.parse(song.contentUri))
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.let {
                duration = convertDuration(it)
            }
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)?.let {
                author = it
            }
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)?.let {
                name = it
            }
            songNew = Song(song.id, name, author, duration, song.contentUri)
        }
        return songNew
    }

    private fun getPicture(context: Context, song: Song): Bitmap? {
        var picture: Bitmap? = null
        MediaMetadataRetriever().apply {
            setDataSource(context, Uri.parse(song.contentUri))
            embeddedPicture?.let {
                picture = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        }
        return picture
    }

    private fun convertDuration(duration: String?): String {
        var result = "00:00"
        duration?.let {
            val durationInt = it.toInt()
            val second = ((durationInt / 1000) % 60).toString().padStart(2, '0')
            val minute = ((durationInt / 1000) / 60).toString().padStart(2, '0')
            result = "$minute:$second"
        }
        return result
    }
}
