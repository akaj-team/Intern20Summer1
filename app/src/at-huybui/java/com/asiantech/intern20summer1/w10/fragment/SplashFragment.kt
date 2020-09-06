package com.asiantech.intern20summer1.w10.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is SplashFragment class. It is fragment to display splash page
 */

class SplashFragment : Fragment() {

    companion object {
        private const val FINISH_TIMER = 60000L
        private const val STEP_TIMER = 20L
        private const val TICK_INTERNET = 20
        internal fun newInstance() = SplashFragment()
    }

    private var callApi: ApiAccountService? = null
    private var dialog: AlertDialog? = null
    private var count = 0

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

    override fun onResume() {
        super.onResume()
        dialog?.dismiss()
    }

    private fun handleProgressSplash() {
        val timer = object : CountDownTimer(FINISH_TIMER, STEP_TIMER) {
            override fun onTick(p0: Long) {
                count++
                when (count) {
                    TICK_INTERNET -> {
                        val isLogin = AppUtils().getIsLogin(requireContext())
                        if (isCheckInternet()) {
                            this.cancel()
                            if (isLogin) {
                                autoSignIn(AppUtils().getToken(requireContext()))
                            } else {
                                (activity as ApiMainActivity).replaceFragment(SignInFragment.newInstance())
                            }
                        } else {
                            selectInternetDialog()
                        }
                    }
                }
            }

            override fun onFinish() {
                AppUtils().showToast(requireContext(), "Hết thời gian chờ")
                (activity as ApiMainActivity).finish()
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

    private fun isCheckInternet(): Boolean {
        var returnValue = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        returnValue = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        returnValue = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        returnValue = true
                    }
                }
            }
        }
        return returnValue
    }

    private fun selectInternetDialog() {
        val builder = (activity as ApiMainActivity).let { AlertDialog.Builder(it) }
        builder.setTitle("Yêu cầu kết nối internet")
        val select = arrayOf("wifi", "netword"
        )
        builder.setItems(select) { _, which ->
            when (which) {
                0 -> {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
                1 -> {
                    startActivity(Intent(Settings.ACTION_DATA_ROAMING_SETTINGS))
                }
            }
        }
        dialog = builder.create()
        dialog?.setOnDismissListener {
            count = 0
        }
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.show()
    }
}
