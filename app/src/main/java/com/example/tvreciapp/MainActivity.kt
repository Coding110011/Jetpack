package com.example.tvreciapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tvreciapp.navigation.Screen
import com.example.tvreciapp.ui.screens.DetailScreen
import com.example.tvreciapp.ui.screens.MainScreen
import com.example.tvreciapp.ui.theme.TvReciAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.tvreciapp.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvReciAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.Main.route) {
                        composable(Screen.Main.route) {
                            MainScreen(
                                viewModel = viewModel,
                                onCharacterClick = { id, isPokemon ->
                                    val route = if (isPokemon) {
                                        Screen.PokemonDetail.createRoute(id)
                                    } else {
                                        Screen.RickAndMortyDetail.createRoute(id)
                                    }
                                    navController.navigate(route)
                                }
                            )
                        }

                        composable(
                            route = Screen.RickAndMortyDetail.route,
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            DetailScreen(
                                viewModel = viewModel,
                                characterId = backStackEntry.arguments?.getInt("id") ?: 0,
                                isPokemon = false,
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        composable(
                            route = Screen.PokemonDetail.route,
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            DetailScreen(
                                viewModel = viewModel,
                                characterId = backStackEntry.arguments?.getInt("id") ?: 0,
                                isPokemon = true,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}