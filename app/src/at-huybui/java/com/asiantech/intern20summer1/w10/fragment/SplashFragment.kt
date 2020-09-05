package com.asiantech.intern20summer1.w10.fragment

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
import com.asiantech.intern20summer1.w10.api.ErrorUtils
import com.asiantech.intern20summer1.w10.models.Account
import com.asiantech.intern20summer1.w10.utils.AppUtils
import retrofit2.Call
import retrofit2.Response

class SplashFragment : Fragment() {

    companion object {
        private const val FINISH_TIMER = 50000L
        private const val STEP_TIMER = 20L
        private const val TICK_LOGIN = 10
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
        val isLogin = AppUtils().getIsLogin(requireContext())
        val timer = object : CountDownTimer(FINISH_TIMER, STEP_TIMER) {
            override fun onTick(p0: Long) {
                count++
                when (count) {
                    TICK_LOGIN -> {
                        if (isLogin) {
                            autoSignIn(AppUtils().getToken(requireContext()))
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
                    if (response.isSuccessful) {
                        response.body()?.let { account ->
                            AppUtils().putIsLogin(requireContext(), true)
                            AppUtils().putToken(requireContext(), account.token)
                            AppUtils().putIdUser(requireContext(), account.id)
                            (activity as ApiMainActivity).replaceFragment(
                                HomeFragment.newInstance()
                            )
                        }
                    } else {
                        val error = ErrorUtils().parseError(response)
                        if (error?.message == Api.MESSAGE_UNAUTHORIZED) {
                            (activity as ApiMainActivity).replaceFragment(SignInFragment.newInstance())
                        }
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {}
            })
        }
    }
}
