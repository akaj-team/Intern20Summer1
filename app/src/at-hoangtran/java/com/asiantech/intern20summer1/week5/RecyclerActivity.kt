package com.asiantech.intern20summer1.week5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import java.util.regex.Pattern

class RecyclerActivity : AppCompatActivity() {
    val i =  Pattern.compile("""^[a-z]+(.[a-z][a-z0-9])$""")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w5_recycler_activity)
    }
}