package com.asiantech.intern20summer1.week7.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-longphan`.activity_register_w7.*

class RegisterWeekSevenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_w7)
        configStatusBarColor()
        handleListeners()
    }

    private fun configStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun handleListeners() {
        handleEditTextUsernameListeners()
        handleEditTextUniversityListeners()
        handleEditTextHomeTownListeners()
    }

    private fun handleEditTextUsernameListeners() {
        tvUsernameWeek7?.addTextChangedListener {
            isButtonNextEnable()
        }
    }

    private fun handleEditTextUniversityListeners() {
        tvUniversityWeek7?.addTextChangedListener {
            isButtonNextEnable()
        }
    }

    private fun handleEditTextHomeTownListeners() {
        tvHomeTownWeek7?.addTextChangedListener {
            isButtonNextEnable()
        }
    }

    @SuppressLint("ResourceType")
    private fun isButtonNextEnable() {
        btnNextRegisterWeek7.let {
            if (tvUsernameWeek7.text.toString().isNotEmpty()
                && tvUniversityWeek7.text.toString().isNotEmpty()
                && tvHomeTownWeek7.text.toString().isNotEmpty()
            ) {
                it.setBackgroundResource(R.drawable.bg_button_next_register_enable_w7)
                it.setTextColor(Color.parseColor(resources.getString(R.color.colorButtonNextRegisterEnableWeek7Text)))
                it.isEnabled = true
            } else {
                it.setBackgroundResource(R.drawable.bg_button_next_register_disable_w7)
                it.setTextColor(Color.parseColor(resources.getString(R.color.colorButtonNextRegisterDisableWeek7Text)))
                it.isEnabled = false
            }
        }
    }
}
