package activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import data.User
import extension.replaceFragment
import fragment.FragmentLogin
import fragment.FragmentRegister

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        replaceFragment(R.id.frContainer, FragmentLogin(), false)
    }

    internal fun openSignUp() {
        replaceFragment(R.id.frContainer, FragmentRegister(), false)
    }

    internal fun openRegister(data: User) {
        replaceFragment(R.id.frContainer, FragmentLogin.newInstance(data), false)
    }
}
