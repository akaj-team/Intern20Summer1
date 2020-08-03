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
        Player(activity?.getString(R.string.w6_position_Forward), R.drawable.img_martial, activity?.getString(R.string.w6_player_1), R.drawable.ic_france),
        Player(activity?.getString(R.string.w6_position_Forward), R.drawable.img_marcus, activity?.getString(R.string.w6_player_2), R.drawable.ic_england),
        Player(activity?.getString(R.string.w6_position_Forward), R.drawable.img_greenwood, activity?.getString(R.string.w6_player_3), R.drawable.ic_england),
        Player(activity?.getString(R.string.w6_position_Midfield), R.drawable.img_bruno_fernandes, activity?.getString(R.string.w6_player_4), R.drawable.ic_portugal),
        Player(activity?.getString(R.string.w6_position_Midfield), R.drawable.img_pogba_2, activity?.getString(R.string.w6_player_5), R.drawable.ic_france),
        Player(activity?.getString(R.string.w6_position_Midfield), R.drawable.img_matic, activity?.getString(R.string.w6_player_6), R.drawable.ic_serbia),
        Player(activity?.getString(R.string.w6_position_Defender), R.drawable.img_luke_shaw, activity?.getString(R.string.w6_player_7), R.drawable.ic_england),
        Player(activity?.getString(R.string.w6_position_Defender), R.drawable.img_harry_maguire, activity?.getString(R.string.w6_player_8), R.drawable.ic_england),
        Player(activity?.getString(R.string.w6_position_Defender), R.drawable.img_lindelof, activity?.getString(R.string.w6_player_9), R.drawable.ic_sweden),
        Player(activity?.getString(R.string.w6_position_Defender), R.drawable.img_wanbi, activity?.getString(R.string.w6_player_10), R.drawable.ic_england),
        Player(activity?.getString(R.string.w6_position_Goalkeeper), R.drawable.img_de_gea, activity?.getString(R.string.w6_player_11), R.drawable.ic_spain)
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
            0 -> loadData()
            1 -> loadData()
            2 -> loadData()
        }
    }
    private fun loadData(){
        val player = players[Random.nextInt(1, players.size)]
        imgHomeFragment.setImageResource(player.image)
        tvPosition.text = player.position
        tvName.text = player.name
        imgNational.setImageResource(player.national)
    }
}
