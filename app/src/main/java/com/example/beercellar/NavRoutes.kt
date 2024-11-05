package com.example.beercellar

import okhttp3.Route

sealed class NavRoutes(val route: String) {

    data object Authentication : NavRoutes("authentication")
    data object BeerList: NavRoutes("list")
    data object BeerDetails: NavRoutes("details")
    data object BeerAdd: NavRoutes("add")
}