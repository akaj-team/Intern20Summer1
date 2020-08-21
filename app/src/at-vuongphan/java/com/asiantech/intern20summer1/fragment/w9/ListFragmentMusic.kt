package com.asiantech.intern20summer1.fragment.w9

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.w9.MusicAdapter
import com.asiantech.intern20summer1.data.w9.Music
import com.asiantech.intern20summer1.data.w9.MusicData
import kotlinx.android.synthetic.`at-vuongphan`.w9_fragment_list_music.*

class ListFragmentMusic : Fragment() {
    private val music = mutableListOf<Music>()
    private var adapterRecycler = MusicAdapter(music)
    private lateinit var musicAdapter: MusicAdapter

    companion object {
        internal fun newInstance(): ListFragmentMusic {
            return ListFragmentMusic()
        }

        private const val PERMISSION_CODE = 101
        private const val DEFAULT_VALUE = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_list_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        initAdapter()
        initData()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.let {
                    context?.let { context ->
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )
                    }
                }
                == PackageManager.PERMISSION_DENIED ||
                this.let {
                    context?.let { context ->
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
                == PackageManager.PERMISSION_DENIED) {
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            }
        }
    }

    private fun initAdapter() {
        rvMusic.adapter = adapterRecycler
        rvMusic.layoutManager = LinearLayoutManager(context)
        rvMusic.setHasFixedSize(true)
    }

    private fun initData() {
        music.clear()
        music.addAll(MusicData.getMusic(requireContext()))
    }
}
