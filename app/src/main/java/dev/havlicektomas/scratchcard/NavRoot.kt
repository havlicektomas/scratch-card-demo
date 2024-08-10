package dev.havlicektomas.scratchcard

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.havlicektomas.scratchcard.detail.presentation.DetailScreenRoot
import dev.havlicektomas.scratchcard.home.presentation.HomeScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute
    ) {
        composable<HomeScreenRoute> {
            HomeScreenRoot(
                onNavigate = {
                    navController.navigate(DetailScreenRoute)
                }
            )
        }
        composable<DetailScreenRoute> {
            DetailScreenRoot(
                onNavigate = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@Serializable
object HomeScreenRoute

@Serializable
object DetailScreenRoute