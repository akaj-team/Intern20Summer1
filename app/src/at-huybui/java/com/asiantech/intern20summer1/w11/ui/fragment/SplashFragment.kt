package com.asiantech.intern20summer1.w11.ui.fragment

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
import com.asiantech.intern20summer1.w11.data.api.ApiClient
import com.asiantech.intern20summer1.w11.data.api.ErrorUtils
import com.asiantech.intern20summer1.w11.data.repository.RemoteRepository
import com.asiantech.intern20summer1.w11.ui.activity.ApiMainActivity
import com.asiantech.intern20summer1.w11.ui.viewmodel.LauncherViewModel
import com.asiantech.intern20summer1.w11.utils.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    private var viewModel: LauncherViewModel? = null
    private var dialog: AlertDialog? = null
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
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
                AppUtils().showToast(requireContext(), getString(R.string.w10_finish_wait_time))
                (activity as ApiMainActivity).finish()
            }

        }
        timer.start()
    }

    private fun autoSignIn(token: String?) {
        token?.let {
            viewModel
                ?.autoSignIn(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { response ->
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
                        if (error?.message == ApiClient.MESSAGE_UNAUTHORIZED) {
                            (activity as ApiMainActivity).replaceFragment(SignInFragment.newInstance())
                        }
                    }
                }
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
        builder.setTitle(getString(R.string.w10_connect_internet_from))
        val select = arrayOf(getString(R.string.w10_wifi), getString(R.string.w10_mobile_network)
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

    private fun setupViewModel() {
        viewModel = LauncherViewModel(RemoteRepository(requireContext()))
    }
}
