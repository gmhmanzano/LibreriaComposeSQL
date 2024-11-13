package com.guillermo.thelibrarycompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guillermo.thelibrarycompose.screens.AddBooks
import com.guillermo.thelibrarycompose.screens.DetailBook
import com.guillermo.thelibrarycompose.screens.HomeScreen

@Composable
fun navigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.Home.route) {
        composable(route = AppScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = AppScreen.AddBook.route){
            AddBooks { navController.popBackStack() }
        }
        composable(route = AppScreen.DetailBook.route + "/{id}") {
            backStateEntry ->
            DetailBook(navController, backStateEntry.arguments!!.getString("id").orEmpty())
        }

    }
}