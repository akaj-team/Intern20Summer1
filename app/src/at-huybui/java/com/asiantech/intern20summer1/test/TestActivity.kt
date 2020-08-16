package com.asiantech.intern20summer1.test

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_test.*

class TestActivity : AppCompatActivity() {

    companion object {
        private const val KEY_PREF = "KEY"
        private const val KEY_DATA = "KEY_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()

    }

    private fun initView() {
        btnTest1?.setOnClickListener {
            val keyPref = edtKeyPre?.text.toString()
            val pref = this.getSharedPreferences(keyPref, Context.MODE_PRIVATE)
            val key = edtKeyData?.text.toString()
            val data = edtData?.text.toString()
            pref.edit().putString(key, data).apply()
        }

        btnTest2?.setOnClickListener {
            val keyPref = edtKeyPre?.text.toString()
            val pref = this.getSharedPreferences(keyPref, Context.MODE_PRIVATE)
            val key = edtKeyData?.text.toString()
            val data = pref.getString(key, "null")
            tvTest1?.text = data
        }
    }
}