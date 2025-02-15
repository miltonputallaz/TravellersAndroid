package com.sani.corporation.travellers

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.sani.corporation.travellers.auth.AuthTravellersNavGraph
import com.sani.corporation.travellers.main.TravellersNavGraph

@Composable
fun InitialScreen() {
    val activityViewModel: TravellersViewModel = hiltViewModel()
    val loginState = activityViewModel.loginState.collectAsState()
    TravellersTheme {
        when (loginState.value) {
            LoginState.UNKNOWN -> WaitingLoginScreen()
            LoginState.NOT_LOGGED_IN -> AuthTravellersNavGraph()
            LoginState.LOGGED_IN -> TravellersNavGraph()
        }
    }
}


@Composable
fun WaitingLoginScreen() {
    Box {
        Text(text = "Loading")
    }
}