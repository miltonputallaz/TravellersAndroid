package com.sani.corporation.travellers.login

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sani.corporation.travellers.HiltTestActivity
import com.sani.corporation.travellers.InitialScreen
import com.sani.corporation.travellers.TravellersTheme
import com.sani.corporation.travellers.auth.AuthTravellersNavGraph
import com.sani.corporation.travellers.auth.login.LoginScreen
import com.sani.corporation.travellers.auth.login.LoginViewModel
import com.sani.corporation.travellers.data.AuthRepository
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.util.network.AsyncState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class LoginScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var authRepository: AuthRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLoginSuccessfully() {
        coEvery { authRepository.login(any()) } returns flow { emit(AsyncState.Success(
            NetworkLoginResponse("token", "")
        )) }
        composeTestRule.setContent {
            InitialScreen()
        }
        composeTestRule.onNodeWithText("Username").performTextInput("miltonputallaz@gmail.com")
        composeTestRule.onNodeWithText("Password").performTextInput("12347412")
        composeTestRule.onNodeWithText("Login").performClick()
    }
}