package activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import fragment.FragmentSignIn
import fragment.FragmentSignUp
import extension.replaceFragment

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        replaceFragment(R.id.frContainer, FragmentSignIn(), false)
    }

    internal fun openSignUp() {
        replaceFragment(R.id.frContainer, FragmentSignUp(), true)
    }
}
