package com.guillermo.thelibrarycompose.navigation

import kotlinx.serialization.Serializable

sealed class AppScreen(
    val route: String
){
    object Home: AppScreen("Home_Screen")
    object AddBook: AppScreen("Add_Book")
    object DetailBook: AppScreen("Detail_Book")
}