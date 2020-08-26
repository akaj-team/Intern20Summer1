package com.asiantech.intern20summer1.week9ver1.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9ver1.model.Song


class SongAdapter : BaseAdapter {

    constructor(context: Context, theSongs: ArrayList<Song>){
        this.songs = theSongs
        this.songInf = LayoutInflater.from(context)
    }

    private lateinit var songs : ArrayList<Song>
    private lateinit var songInf : LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        //map to song layout
        val songLay = songInf.inflate(R.layout.item_list_song_w9v1, parent, false) as? LinearLayout
        //get title and artist views
        val songView =
            songLay?.findViewById<View>(R.id.song_title) as? TextView
        val artistView =
            songLay?.findViewById<View>(R.id.song_artist) as? TextView
        //get song using position
        val currSong = songs[position]
        //get title and artist strings
        songView?.text = currSong.title
        artistView?.text = currSong.artist
        //set position as tag
        songLay?.tag = position

        return songLay
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return  songs.size
    }
}
