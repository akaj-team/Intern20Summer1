package w4FragmentIntentActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R

class LoginActivity : AppCompatActivity() {
    @SuppressLint("ShowToast", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w4_activity_login)
        showLoginFragment()
    }

    private fun showLoginFragment(){
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(
            R.id.fl_container,
            LoginFragment()
        ).commit()
    }
}
