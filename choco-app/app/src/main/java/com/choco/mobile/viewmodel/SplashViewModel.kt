package com.choco.mobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choco.core.auth.UserSessionManager
import com.choco.core.util.Resource

class SplashViewModel(
    private val userSessionManager: UserSessionManager,
    private val statusMutableLiveData: MutableLiveData<Resource<Status>> = MutableLiveData()
) : ViewModel() {
    val statusLiveData: LiveData<Resource<Status>>
        get() = statusMutableLiveData

    fun getLaunchStatus() {
        statusMutableLiveData.postValue(Resource.loading())
        statusMutableLiveData.postValue(
            Resource.success(
                Status(userSessionManager.isUserLoggedIn())
            )
        )
    }

    data class Status(val isAuthed: Boolean)
}
