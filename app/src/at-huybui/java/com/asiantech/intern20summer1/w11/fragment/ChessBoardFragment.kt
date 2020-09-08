package com.asiantech.intern20summer1.w11.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 07/09/2020.
 * This is ChessBoardFragment class for chess board fragment.
 * It will contain chess board view
 */
class ChessBoardFragment : Fragment() {

    companion object {
        internal fun newInstance() = ChessBoardFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w11_fragment_chess_board, container, false)
    }

}
