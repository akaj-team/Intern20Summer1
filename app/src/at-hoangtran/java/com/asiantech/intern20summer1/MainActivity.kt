package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.week4.LayoutActivity
import com.asiantech.intern20summer1.week6.IndicatorActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_main2.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        handleWeek4Button()
        handleWeek6Button()
    }

    private fun handleWeek4Button() {
        btnWeek4?.setOnClickListener {
            val intent = Intent(this, LayoutActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    private fun handleWeek5Button() {
//        btn_week5.setOnClickListener {
//            val intent = Intent(this, LayoutActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

    private fun onTextChange(edt: EditText, tick: ImageView) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                tick.visibility = View.VISIBLE
                if (str.isEmpty()) {
                    tick.visibility = View.INVISIBLE
                    edt.setBackgroundResource(R.drawable.bg_edit_text_focus)
                } else when (edt) {
                    edtEmail -> {
                        if (Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                            tick.setImageResource(R.mipmap.icon_tick)
                        } else {
                            tick.setImageResource(R.mipmap.icon_error)
                        }
                    }
                    edtPass -> {
                        if (isValidPassword(str)) {
                            tick.setImageResource(R.mipmap.icon_tick)
                            edt.setBackgroundResource(R.drawable.bg_edit_text_focus)
                        } else {
                            tick.setImageResource(R.mipmap.icon_error)
                            edt.setBackgroundResource(R.drawable.bg_wrong_password)
                        }
                    }
                    edtRetype -> {
                        if (str == edtPass.text.toString() && isValidPassword(str)) {
                            tick.setImageResource(R.mipmap.icon_tick)
                            edt.setBackgroundResource(R.drawable.bg_edit_text_focus)
                        } else {
                            tick.setImageResource(R.mipmap.icon_error)
                            edt.setBackgroundResource(R.drawable.bg_wrong_password)
                        }
                    }
                }
            }
        })
    }
}
