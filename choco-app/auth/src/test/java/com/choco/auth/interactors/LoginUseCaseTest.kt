package com.choco.auth.interactors

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.choco.auth.model.LoginResponse
import com.choco.auth.repo.AuthRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
class LoginUseCaseTest {
    lateinit var useCase: LoginUseCase
    @MockK lateinit var repo: AuthRepository

    private val stubUserName = "user"
    private val stubPassword = "p"
    private val token = "t"
    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = LoginUseCase(repo)
    }

    @Test
    fun `should call repo`() = runBlockingTest {
        val stubResponse = LoginResponse(token)
        coEvery {
            repo.login(any())
        } returns stubResponse

        val result = useCase.login(stubUserName, stubPassword)
        Assert.assertEquals(token, result)
    }
}