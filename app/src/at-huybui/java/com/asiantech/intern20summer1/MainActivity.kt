package com.asiantech.intern20summer1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.`at-huybui`.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleForEdittextEmail()


        btnSignup.setOnClickListener {
            Log.d("AAA","Click buton")
        }


    }

    private fun handleForEdittextEmail(){
        edtEmail.setOnFocusChangeListener { view, b ->
            if(b == true){
                printLog("chọn email Edittext")

            }else{
                printLog("bỏ chọn email Edittext")
            }
        }

        edtEmail.addTextChangedListener{
            printLog("change text")
        }

    }

    private fun printLog(st :String){
        Log.d("AAA",st)
    }
}
