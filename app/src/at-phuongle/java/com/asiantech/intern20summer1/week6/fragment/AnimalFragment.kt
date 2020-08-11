package com.asiantech.intern20summer1.week6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-phuongle`.fragment_animal.*

class AnimalFragment : Fragment() {
    companion object {
        private const val KEY_POSITION = "key_position"


        fun newInstance(position: Int) = AnimalFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_POSITION, position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleAnimalImageView()
    }

    private fun handleAnimalImageView() {
        when (arguments?.getInt(KEY_POSITION)) {
            0 -> {
                imgAnimal.setImageResource(R.drawable.koala)
            }
            1 -> {
                imgAnimal.setImageResource(R.drawable.giraffe)
            }
            2 -> {
                imgAnimal.setImageResource(R.drawable.panda)
            }
        }
    }
}
