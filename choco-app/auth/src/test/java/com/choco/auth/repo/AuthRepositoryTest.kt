package com.choco.auth.repo

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.choco.auth.model.LoginRequestParams
import com.choco.auth.model.LoginResponse
import com.choco.auth.network.AuthApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
class AuthRepositoryTest {
    lateinit var repository: AuthRepository
    @MockK lateinit var apiService: AuthApiService

    private val stubCredential = LoginRequestParams("user", "password")
    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        repository = AuthRepository(apiService)
    }

    @Test
    fun `test happy flow`() = runBlockingTest{
        val response = LoginResponse("token")
        coEvery { apiService.login(stubCredential) } returns response

        val result = repository.login(stubCredential)
        coVerify { apiService.login(stubCredential) }

        Assert.assertEquals(response, result)
    }

    @Test
    fun `test exception case`() = runBlockingTest {
        val throwable = Exception("Test")

        coEvery { apiService.login(stubCredential) } throws throwable

        try {
            repository.login(stubCredential)
        } catch (e: Throwable) {
            Assert.assertEquals(throwable, e)
        }
        coVerify { apiService.login(stubCredential) }
    }
}