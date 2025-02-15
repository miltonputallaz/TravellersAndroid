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

package com.sani.corporation.travellers.main

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.sani.corporation.travellers.main.TravellersDestinationsArgs.TITLE_ARG
import com.sani.corporation.travellers.main.TravellersDestinationsArgs.TRAVEL_ID_ARG
import com.sani.corporation.travellers.main.TravellersDestinationsArgs.USER_MESSAGE_ARG
import com.sani.corporation.travellers.main.TravellersScreens.ADD_EDIT_TRAVEL_SCREEN
import com.sani.corporation.travellers.main.TravellersScreens.STATISTICS_SCREEN
import com.sani.corporation.travellers.main.TravellersScreens.TRAVELS_SCREEN
import com.sani.corporation.travellers.main.TravellersScreens.TRAVEL_DETAIL_SCREEN

/**
 * Screens used in [TravellersDestinations]
 */
private object TravellersScreens {
    const val TRAVELS_SCREEN = "travels"
    const val STATISTICS_SCREEN = "statistics"
    const val TRAVEL_DETAIL_SCREEN = "travel"
    const val ADD_EDIT_TRAVEL_SCREEN = "addEditTravel"
}

/**
 * Arguments used in [TravellersDestinations] routes
 */
object TravellersDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TRAVEL_ID_ARG = "travelId"
    const val TITLE_ARG = "title"
}

/**
 * Destinations used in the [TravellersActivity]
 */
object TravellersDestinations {
    const val TRAVELS_ROUTE = "$TRAVELS_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val STATISTICS_ROUTE = STATISTICS_SCREEN
    const val TASK_DETAIL_ROUTE = "$TRAVEL_DETAIL_SCREEN/{$TRAVEL_ID_ARG}"
    const val ADD_EDIT_TRAVEL_ROUTE = "$ADD_EDIT_TRAVEL_SCREEN/{$TITLE_ARG}?$TRAVEL_ID_ARG={$TRAVEL_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class TravellersNavigationActions(private val navController: NavHostController) {

    fun navigateToTasks(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            TRAVELS_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToStatistics() {
        navController.navigate(TravellersDestinations.STATISTICS_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToTaskDetail(taskId: String) {
        navController.navigate("$TRAVEL_DETAIL_SCREEN/$taskId")
    }

    fun navigateToAddEditTask(title: Int, travelId: String?) {
        navController.navigate(
            "$ADD_EDIT_TRAVEL_SCREEN/$title".let {
                if (travelId != null) "$it?$TRAVEL_ID_ARG=$travelId" else it
            }
        )
    }
}
