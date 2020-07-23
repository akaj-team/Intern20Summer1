package com.asiantech.intern20summer1.w5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.intern20summer1.R


class ListAdapter(private val mutableList: MutableList<ItemRecycler>)
    : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}


class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout., parent, false)) {

}
