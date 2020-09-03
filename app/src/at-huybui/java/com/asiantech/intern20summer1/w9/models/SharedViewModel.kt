package com.asiantech.intern20summer1.w9.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by huy-bui-98 on 08/21/20
 * This is ViewModel for activity
 */

class SharedViewModel : ViewModel() {
    var songLists = MutableLiveData<MutableList<Song>>()

    fun setSongLists(list: MutableList<Song>) {
        songLists.value = list
    }
}
