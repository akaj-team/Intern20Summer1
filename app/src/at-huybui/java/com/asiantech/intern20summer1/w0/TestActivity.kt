package com.asiantech.intern20summer1.w0

import android.os.Bundle
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
                        if (error?.statusCode == ErrorUtils.BAD_REQUEST_CODE && error.message ==
                            ErrorUtils.MESSAGE_EMAIL_HAS_BEEN_TAKEN
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
            var email = edtEmail_Test.text.toString()
            var pass = edtPass_Test.text.toString()
            callApi?.login(email,pass)?.enqueue(object : retrofit2.Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.body() == null) {
                        d("test","[login: mess]" + response.body().toString())
                        d("test", response.code().toString())
                    } else {
                        d("test","[login: mess]" + response.body().toString())
                        d("test","[login]" +  response.body().toString())
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                }
            })
        }
    }
}