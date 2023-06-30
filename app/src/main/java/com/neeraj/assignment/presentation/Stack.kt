package com.neeraj.assignment.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neeraj.assignment.presentation.facilities.FacilitiesListScreen

@Composable
fun AppStack() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.FacilitiesListScreen.route
    ) {
        composable(route = Screen.FacilitiesListScreen.route) {
            FacilitiesListScreen()
        }
    }
}