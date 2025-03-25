package com.example.airport

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.airport.data.repository.AuthRepository
import com.example.airport.ui.screens.LoginScreen
import com.example.airport.ui.screens.NavigationScreen
import com.example.airport.utils.PreferenceHelper

object Routes {
    const val LOGIN = "login"
    const val NAVIGATION = "navigation"
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authRepository: AuthRepository,
    preferenceHelper: PreferenceHelper
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.NAVIGATION) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                authRepository = authRepository,
                preferenceHelper = preferenceHelper
            )
        }
        composable(Routes.NAVIGATION) {
            NavigationScreen(
                onNavigate = { route ->
                    when (route) {
                        "logout" -> navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }
                        }
                        // Các tuyến đường khác
                    }
                },
                authRepository = authRepository,
                preferenceHelper = preferenceHelper
            )
        }
    }
}