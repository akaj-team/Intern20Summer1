package com.asiantech.intern20summer1.w11.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w11.api.ApiRepos
import com.asiantech.intern20summer1.w11.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 14/09/2020.
 * This is HomeViewModel TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class HomeViewModel(private val repos: ApiRepos) :
    ViewModel() {
    private val users = MutableLiveData<Resource<List<PostItem>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        val token = "274d583fe5905b140f506656b6a5200f"
        users.postValue(Resource.loading(null))
        compositeDisposable.add(
            repos.getPosts(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    users.postValue(Resource.success(userList))
                }, { throwable ->
                    users.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getPosts(): LiveData<Resource<List<PostItem>>> {
        return users
    }
}
     