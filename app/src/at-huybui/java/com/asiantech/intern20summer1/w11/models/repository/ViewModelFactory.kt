package com.asiantech.intern20summer1.w11.models.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asiantech.intern20summer1.w11.api.ApiHelper
import com.asiantech.intern20summer1.w11.api.ApiRepos
import com.asiantech.intern20summer1.w11.models.HomeViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(ApiRepos(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}