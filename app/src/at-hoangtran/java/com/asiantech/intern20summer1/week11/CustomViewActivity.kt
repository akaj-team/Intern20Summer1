package com.asiantech.intern20summer1.week11

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_custom_view.*

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        handleButton(ChessBoardFragment(), btnChessBoard)
        handleButton(WeightDataFragment(), btnWeightData)
    }

    private fun handleButton(fragment: Fragment, btn: Button) {
        btn.setOnClickListener {
            val trans = supportFragmentManager.beginTransaction()
            trans.replace(R.id.flContainer, fragment).commit()
        }
    }
}
