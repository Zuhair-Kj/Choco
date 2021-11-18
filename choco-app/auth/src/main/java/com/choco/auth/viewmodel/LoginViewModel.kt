package com.choco.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choco.auth.interactors.LoginUseCase
import com.choco.core.auth.UserSessionManager
import com.choco.core.util.DispatchersProvider
import com.choco.core.util.NetworkHelper
import com.choco.core.util.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val networkHelper: NetworkHelper,
    private val loginMutableLiveData: MutableLiveData<Resource<String>> = MutableLiveData(),
    private val userSessionManager: UserSessionManager,
    private val dispatchersProvider: DispatchersProvider
): ViewModel() {

    val loginLiveData: LiveData<Resource<String>>
        get() = loginMutableLiveData

    fun login(userName: String, password: String) {
        dispatchersProvider.viewModelScope(this).launch(dispatchersProvider.io()) {
            try {
                if (!networkHelper.isConnected()) {
                    loginMutableLiveData.postValue(Resource.networkError())
                    return@launch
                }
                loginMutableLiveData.postValue(Resource.loading())
                val token = loginUseCase.login(userName, password)
                if (token.isNullOrBlank())
                    loginMutableLiveData.postValue(Resource.error())
                else {
                    userSessionManager.storeToken(token)
                    loginMutableLiveData.postValue(Resource.success(token))
                }

            } catch (throwable: Throwable) {
                loginMutableLiveData.postValue(Resource.error())
            }
        }
    }

}