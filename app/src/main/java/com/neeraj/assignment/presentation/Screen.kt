package com.neeraj.assignment.presentation

sealed class Screen(val route: String) {
    object FacilitiesListScreen: Screen("facilities")
}