package com.asiantech.intern20summer1.w9.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.adapter.SongAdapter
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-sonnguyen`.w9_fragment_song_list.*

class SongListFragment : Fragment() {
    companion object {
        private const val PERMISSION_CODE = 1001
        private const val DEFAULT_VALUE = 0
        internal fun instance() = SongListFragment()
    }

    private var songs = mutableListOf<Song>()
    private var songAdapter = SongAdapter(songs)
    private var positionSongPlaying =  DEFAULT_VALUE
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        initAdapter()
        initData()
        initListener()
    }

    private fun initAdapter() {
        songAdapter = SongAdapter(songs)
        recyclerViewSongList.adapter = songAdapter
    }

    private fun initData() {
        songs.clear()
        songs.addAll(SongData.getSong(requireContext()))
    }
    private fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (this.let {
                    context?.let {context ->
                        ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA) } } == PackageManager.PERMISSION_DENIED || this.let {
                    context?.let { context -> ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE ) } } == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
        }
    }
    private fun initListener(){
        songAdapter.onItemClicked = {
            positionSongPlaying = it
            setCardViewData()
        }
    }
    private fun setCardViewData(){
        Glide.with(requireContext())
            .load(songs[positionSongPlaying].imageUri)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imgSmallSong)
        tvCardViewSongName.text = songs[positionSongPlaying].songName
    }
}
