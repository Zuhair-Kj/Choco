package com.choco.auth.viewModel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.choco.auth.interactors.LoginUseCase
import com.choco.auth.viewmodel.LoginViewModel
import com.choco.core.auth.UserSessionManager
import com.choco.core.util.NetworkHelper
import com.choco.core.util.Resource
import com.choco.test.TestDispatchersProvider
import com.choco.test.UnitTestState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
class LoginViewModelTest {
    lateinit var viewModel: LoginViewModel

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @MockK
    lateinit var loginUseCase: LoginUseCase
    @MockK
    lateinit var networkHelper: NetworkHelper
    @MockK
    lateinit var userSessionManager: UserSessionManager
    private val testDispatchersProvider = TestDispatchersProvider()
    @SpyK
    var testState = UnitTestState<Resource<String>>()

    private val stubUserName = "user"
    private val stubPassword = "p"
    private val token = "t"

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = LoginViewModel(
            loginUseCase,
            networkHelper = networkHelper,
            userSessionManager = userSessionManager,
            dispatchersProvider = testDispatchersProvider
        )
    }

    @Test
    fun `test success case`() {
        coEvery { networkHelper.isConnected() } returns true
        coEvery { loginUseCase.login(stubUserName, stubPassword) } returns token

        viewModel.loginLiveData.observeForever {
            testState.obj = it
        }

        viewModel.login(stubUserName, stubPassword)

        coVerify(exactly = 1) { loginUseCase.login(stubUserName, stubPassword) }
        coVerify(exactly = 1) { networkHelper.isConnected() }
        coVerifySequence {
            testState.obj = Resource.loading()
            testState.obj = Resource.success(token)
        }

    }

    @Test
    fun `test offline case`() {
        coEvery { networkHelper.isConnected() } returns false

        viewModel.loginLiveData.observeForever {
            testState.obj = it
        }

        viewModel.login(stubUserName, stubPassword)

        coVerifySequence {
            testState.obj = Resource.networkError()
        }
        coVerify(exactly = 0) { loginUseCase.login(stubUserName, stubPassword) }
    }

    @Test
    fun `test error case`() {
        coEvery { networkHelper.isConnected() } returns true
        coEvery { loginUseCase.login(stubUserName, stubPassword) } throws RuntimeException("")

        viewModel.loginLiveData.observeForever {
            testState.obj = it
        }

        viewModel.login(stubUserName, stubPassword)

        coVerifySequence {
            testState.obj = Resource.loading()
            testState.obj = Resource.error()
        }
        coVerify(exactly = 1) { loginUseCase.login(stubUserName, stubPassword) }
    }
}