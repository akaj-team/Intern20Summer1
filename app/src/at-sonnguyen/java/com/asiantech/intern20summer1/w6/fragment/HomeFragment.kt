package com.asiantech.intern20summer1.w6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w6.data.Player
import kotlinx.android.synthetic.`at-sonnguyen`.w6_home_fragment.*
import kotlin.random.Random

class HomeFragment : Fragment() {

    private var position = 0
    private var players = arrayListOf<Player>(
        Player("ST", R.drawable.img_martial, "Anthony Martial", R.drawable.ic_france),
        Player("LW", R.drawable.img_marcus, "Marcus Rashford", R.drawable.ic_england),
        Player("RW", R.drawable.img_greenwood, "Mason Greenwood", R.drawable.ic_england),
        Player("CAM", R.drawable.img_bruno_fernandes, "Bruno fernandes", R.drawable.ic_portugal),
        Player("CM", R.drawable.img_pogba_2, "Paul Pogba", R.drawable.ic_france),
        Player("CDM", R.drawable.img_matic, "Neimaja Matic", R.drawable.ic_serbia),
        Player("LB", R.drawable.img_luke_shaw, "Luke Shaw", R.drawable.ic_england),
        Player("CB", R.drawable.img_harry_maguire, "Harry Maguire", R.drawable.ic_england),
        Player("CB", R.drawable.img_lindelof, "Victor Lindelof", R.drawable.ic_sweden),
        Player("RB", R.drawable.img_wanbi, "Aaron Wan-Bissaka", R.drawable.ic_england),
        Player("GK", R.drawable.img_de_gea, "David De Gea", R.drawable.ic_spain)
    )

    companion object {
        private const val KEY_VALUE = "position"
        fun newInstance(position: Int): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_VALUE, position)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w6_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(KEY_VALUE) ?: 0
        when (position) {
            0 -> {
                val player = players[Random.nextInt(1, players.size)]
                imgHomeFragment.setImageResource(player.image)
                tvPosition.text = player.position
                tvName.text = player.name
                imgNational.setImageResource(player.national)
            }
            1 -> {
                val player = players[Random.nextInt(1, players.size)]
                imgHomeFragment.setImageResource(player.image)
                tvPosition.text = player.position
                tvName.text = player.name
                imgNational.setImageResource(player.national)
            }
            2 -> {
                val player = players[Random.nextInt(1, players.size)]
                imgHomeFragment.setImageResource(player.image)
                tvPosition.text = player.position
                tvName.text = player.name
                imgNational.setImageResource(player.national)
            }
        }
    }
}
