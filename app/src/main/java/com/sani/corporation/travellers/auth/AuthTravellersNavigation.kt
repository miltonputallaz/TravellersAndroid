/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sani.corporation.travellers.auth

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.sani.corporation.travellers.auth.TravellersScreens.LOGIN_SCREEN
import com.sani.corporation.travellers.auth.TravellersScreens.REGISTER_SCREEN


/**
 * Screens used in [TravellersDestinations]
 */
private object TravellersScreens {
    const val REGISTER_SCREEN = "register"
    const val LOGIN_SCREEN = "login"
}

/**
 * Arguments used in [TravellersDestinations] routes
 */
object TravellersDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}

/**
 * Destinations used in the [TravellersActivity]
 */
object AuthTravellersDestinations {
    const val REGISTER_ROUTE = "$REGISTER_SCREEN"
    const val LOGIN_ROUTE = "$LOGIN_SCREEN"
}

/**
 * Models the navigation actions in the app.
 */
class AuthTravellersNavigationActions(private val navController: NavHostController) {

    fun navigateToLogin(userMessage: Int = 0) {
        navController.navigate(
            LOGIN_SCREEN
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = false
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }
    }

    fun navigateToRegister(userMessage: Int = 0) {
        navController.navigate(
            REGISTER_SCREEN
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = false
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }
    }
}
