package com.asiantech.intern20summer1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.introJava.DarkWilderness
import com.asiantech.intern20summer1.introkotlin.TheJourneyBegins

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var huy : TheJourneyBegins = TheJourneyBegins();

        huy.add(10,20);
    }
}
