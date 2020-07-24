package w4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.w4_activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w4_activity_splash)

        splash_logo.animate().setDuration(5000).alpha(1f).withEndAction {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
