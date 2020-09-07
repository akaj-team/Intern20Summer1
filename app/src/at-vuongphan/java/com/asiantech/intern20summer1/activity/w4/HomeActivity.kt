package com.asiantech.intern20summer1.activity.w4

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.User
import kotlinx.android.synthetic.`at-vuongphan`.home_activity.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        getDataFromLogin()
    }

    private fun getDataFromLogin() {
        (intent.extras?.getParcelable(resources.getString(R.string.key_data_login)) as? User).let {
            edtFullNameHome.setText(it?.name)
            edtEmailHome.setText(it?.email)
            edtNumberPhoneHome.setText(it?.phoneNumber)
            edtPasswordHome.setText(it?.password)
        }
    }
}
