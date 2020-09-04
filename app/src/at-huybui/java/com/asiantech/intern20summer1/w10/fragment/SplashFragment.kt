package com.asiantech.intern20summer1.w10.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import com.asiantech.intern20summer1.w10.api.Api
import com.asiantech.intern20summer1.w10.api.ApiAccountService
import com.asiantech.intern20summer1.w10.models.Account
import retrofit2.Call
import retrofit2.Response

class SplashFragment : Fragment() {

    companion object {
        internal const val NAME_PREFERENCE = "preference"
        internal const val KEY_IS_LOGIN = "key_is_login"
        internal const val KEY_TOKEN_LOGIN = "key_token_login"
        internal fun newInstance() = SplashFragment()
    }

    private var callApi: ApiAccountService? = null
    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callApi = Api.getInstance()?.create(ApiAccountService::class.java)
        return inflater.inflate(R.layout.w10_fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleProgressSplash()

    }

    private fun handleProgressSplash(){
        val preference = requireContext().getSharedPreferences(NAME_PREFERENCE, MODE_PRIVATE)
        val isSignIn = preference.getBoolean(KEY_IS_LOGIN, false)

        val timer = object : CountDownTimer(50000L, 20L) {
            override fun onTick(p0: Long) {
                count++
                when (count) {
                    10 -> {
                        if (isSignIn) {
                            autoSignIn(preference.getString(KEY_TOKEN_LOGIN, ""))
                        } else {
                            (activity as ApiMainActivity).replaceFragment(SignInFragment.newInstance())
                        }
                        this.cancel()
                    }
                }
            }

            override fun onFinish() {
            }

        }
        timer.start()
    }

    private fun autoSignIn(token: String?) {
        token?.let {
            callApi?.autoSignIn(it)?.enqueue(object : retrofit2.Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    response.body()?.let { account ->
                        (activity as ApiMainActivity).replaceFragment(
                            HomeFragment.newInstance(
                                account
                            )
                        )
                    }
                    if (response.body() == null) {
                        (activity as ApiMainActivity).replaceFragment(SignInFragment.newInstance())
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {}
            })
        }
    }
}
