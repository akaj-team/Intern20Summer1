package com.asiantech.intern20summer1.w0

import android.os.Bundle
import android.os.Environment
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiAccountService
import com.asiantech.intern20summer1.w10.api.ErrorUtils
import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.models.RequestAccount
import kotlinx.android.synthetic.`at-huybui`.activity_test.*
import retrofit2.Call
import retrofit2.Response

class TestActivity : AppCompatActivity() {

    private var callApi: ApiAccountService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        callApi = Api.getInstance()?.create(ApiAccountService::class.java)
        initListener()
    }


    private fun initListener() {
        btnPost_Test?.setOnClickListener {
            val email = edtEmail_Test.text.toString()
            val pass = edtPass_Test.text.toString()
            val name = edtName_Test.text.toString()
            val accountTest = RequestAccount(email, pass, name)
            callApi?.createUser(accountTest)?.enqueue(object : retrofit2.Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.isSuccessful) {
                        d("testa", "[sign up: mess]" + response.body().toString())
                        d("testa", "[sign up]" + response.body().toString())
                    } else {
                        val error = ErrorUtils().parseError(response)
                        if (error?.message ==
                            Api.MESSAGE_EMAIL_HAS_BEEN_TAKEN
                        ) {
                            d("testa", "[error: code]" + error.statusCode)
                            d("testa", "[error: mess]" + error.message)
                        }
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                }
            })
        }

        btnGet_Test?.setOnClickListener {
            d("testpart", "1 : " + Environment.getExternalStorageDirectory().toString())
                d("testpart", "2 : " + Environment.getExternalStorageState().toString())
        }
    }
}
