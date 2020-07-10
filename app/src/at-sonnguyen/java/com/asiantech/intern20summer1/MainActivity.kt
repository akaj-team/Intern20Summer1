package com.asiantech.intern20summer1

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R.string
import kotlinx.android.synthetic.`at-sonnguyen`.activity_main.*
import com.asiantech.intern20summer1.R.string.changebackground as changebackground1

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide(); // hide the title bar
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main)
        edtEmail.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

        })
                btnSignUp.setOnClickListener {
            Toast.makeText(this, "click Sign Up" , Toast.LENGTH_LONG ).show()
        }
                txtSignIn.setOnClickListener {
            Toast.makeText(this , "Click Sign In" , Toast.LENGTH_LONG).show()
        }

    }
}

