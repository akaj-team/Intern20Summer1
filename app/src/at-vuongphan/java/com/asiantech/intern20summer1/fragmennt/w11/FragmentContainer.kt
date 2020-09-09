package com.asiantech.intern20summer1.fragmennt.w11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w11.MainActivity
import kotlinx.android.synthetic.main.fragment_container.*

class FragmentContainer : Fragment() {
    companion object {
        internal fun newInstance() = FragmentContainer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonExerciseChess()
        initButtonExerciseWeight()
    }

    private fun initButtonExerciseChess() {
        btnChess?.setOnClickListener {
            (activity as? MainActivity)?.openFragment(FragmentChessBoard.newInstance(), true)
        }
    }

    private fun initButtonExerciseWeight() {
        btnWeight?.setOnClickListener {
            (activity as? MainActivity)?.openFragment(FragmentWeight.newInstance(), true)
        }
    }
}
