package com.asiantech.intern20summer1.fragment.w9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.w9.AdapterMusic
import com.asiantech.intern20summer1.data.w9.ItemMusic
import kotlinx.android.synthetic.`at-vuongphan`.w9_fragment_list_music.*

class FragmentMusic : Fragment() {
    private val exampleLists: MutableList<ItemMusic> = mutableListOf()
    private var adapterRecycler = AdapterMusic(exampleLists)

    companion object {
        internal const val LOOP = 10
        internal fun newInstance(): FragmentMusic {
            return FragmentMusic()
        }
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
        initAdapter()
        exampleLists.addAll(newData())
    }

    private fun initAdapter() {
        rvMusic.adapter = adapterRecycler
        rvMusic.layoutManager = LinearLayoutManager(context)
        rvMusic.setHasFixedSize(true)
    }

    private fun newData(): MutableList<ItemMusic> {
        val posts = mutableListOf<ItemMusic>()
        val images = listOf(
            R.drawable.background,
            R.drawable.ronaldo,
            R.drawable.tran_thi_duyen
        )
        val nameSong = listOf(
            "Anh yeu em",
            "Hon ca yeu",
            "Tam biet nhe"
        )
        val nameArtist = listOf(
            "MiuLe",
            "Duc Phuc",
            "Linkly"
        )
        for (i in 0..LOOP) {
            posts.add(
                ItemMusic(
                    images.random(),
                    nameSong.random(),
                    nameArtist.random()
                )
            )
        }
        return posts
    }
}
