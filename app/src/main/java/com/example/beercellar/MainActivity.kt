package com.example.beercellar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beercellar.model.AuthenticationViewModel
import com.example.beercellar.model.Beer
import com.example.beercellar.model.BeersViewModel
import com.example.beercellar.screens.Authentication
import com.example.beercellar.screens.BeerAdd
import com.example.beercellar.screens.BeerDetails
import com.example.beercellar.screens.BeerList
import com.example.beercellar.ui.theme.BeerCellarTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeerCellarTheme {
                MainScreen()

            }
        }
    }
}




@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: BeersViewModel = viewModel()
    val beers = viewModel.beersFlow.value
    val errorMessage = viewModel.errorMessageFlow.value
    val authenticationViewModel: AuthenticationViewModel = viewModel()

    NavHost(navController = navController, startDestination = NavRoutes.Authentication.route) {
        composable(NavRoutes.Authentication.route) {
            Authentication(
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                register = { email, password -> authenticationViewModel.register(email, password) },
                navigateToBeerList = { navController.navigate(NavRoutes.BeerList.route) }
            )


        }
        composable(NavRoutes.BeerList.route) {
            BeerList(
                modifier = modifier,
                beers = beers,
                errorMessage = errorMessage,
                onBeerSelected =
                { beer -> navController.navigate(NavRoutes.BeerDetails.route + "/${beer.id}") },
                onBeerDeleted = { beer -> viewModel.remove(beer) },
                onAdd = { navController.navigate(NavRoutes.BeerAdd.route) },
                sortByName = { viewModel.sortBeersByName(ascending = it) },
                sortByABV = { viewModel.sortBeersByABV(ascending = it) },
                filterByName = { viewModel.filterByName(it) },
                user = authenticationViewModel.user,
                signOut = { authenticationViewModel.signOut() },
                navigateToAuthentication = {
                    navController.popBackStack(NavRoutes.Authentication.route, inclusive = false)

                })
        }
        composable(
            NavRoutes.BeerDetails.route + "/{beerId}",
            arguments = listOf(navArgument("beerId") { type = NavType.IntType })
        ) { backStackEntry ->
            val beerId = backStackEntry.arguments?.getInt("beerId")
            val beer = beers.find { it.id == beerId } ?: Beer(
                user = "0", brewery = "0", name = "0",
                style = "0", abv = 0.0, volume = 0.0, pictureUrl = "0",
                howMany = 0
            )
            BeerDetails(modifier = modifier,
                beer = beer,
                onUpdate = { id: Int, beer: Beer -> viewModel.update(id, beer) },
                onNavigateBack = { navController.popBackStack() })
        }
        composable(NavRoutes.BeerAdd.route) {
            BeerAdd(modifier = modifier,
                addBeer = { beer -> viewModel.add(beer) },
                navigateBack = { navController.popBackStack() })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BeerListPreview() {
    BeerCellarTheme {
        MainScreen()
    }
}
















