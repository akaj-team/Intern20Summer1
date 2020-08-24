package com.asiantech.intern20summer1.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

    private fun initView() {
        testStart.setOnClickListener {
            imgTest.startAnim()
        }
        testEnd.setOnClickListener {
            imgTest.endAnim()
        }

        testPause.setOnClickListener {
            imgTest.pauseAnim()
        }

        testResume.setOnClickListener {
            imgTest.resumeAnim()
        }
    }
}
