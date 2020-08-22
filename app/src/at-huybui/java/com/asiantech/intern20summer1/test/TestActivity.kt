package com.asiantech.intern20summer1.test

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
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
        btnTest?.setOnClickListener {
        }

    }
}
