package com.asiantech.intern20summer1.week12.viewmodels

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.week12.api.ApiClient


class HomeViewModel : ViewModel(){
    internal fun getListPost(token : String) = ApiClient.cretePostService()?.getListPost(token)
}