package com.example.navigation.navigation

sealed class Screen (val route: String){
    object Splash: Screen("splash")
    object Onboarding1: Screen("onboarding1")
    object Onboarding2: Screen("onboarding2")
    object Onboarding3: Screen("onboarding3")
    object Home: Screen("home")
}