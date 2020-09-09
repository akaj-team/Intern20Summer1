package com.asiantech.intern20summer1.fragmennt.w11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

class FragmentChessBoard : Fragment() {
    companion object {
        internal fun newInstance() = FragmentChessBoard()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w11_fragment_chess_board, container, false)
    }
}
