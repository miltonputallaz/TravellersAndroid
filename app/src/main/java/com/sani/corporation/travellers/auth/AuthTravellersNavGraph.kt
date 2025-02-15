package com.sani.corporation.travellers.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sani.corporation.travellers.auth.login.LoginScreen
import com.sani.corporation.travellers.auth.register.RegisterScreen

import kotlinx.coroutines.CoroutineScope

@Composable
fun AuthTravellersNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AuthTravellersDestinations.LOGIN_ROUTE,
    navActions: AuthTravellersNavigationActions = remember(navController) {
        AuthTravellersNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            AuthTravellersDestinations.LOGIN_ROUTE
        ) { entry ->
            LoginScreen(modifier = modifier)
        }
        composable(
            AuthTravellersDestinations.REGISTER_ROUTE
        ) { entry ->
            RegisterScreen(modifier = modifier)
        }
    }
}

// Keys for navigation

