package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import com.example.navigation.navigation.Screen
import androidx.navigation.compose.NavHost
import com.example.navigation.ui.theme.HomeScreen
import com.example.navigation.ui.theme.OnboardingScreen
import com.example.navigation.ui.theme.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route
            ){
                composable(Screen.Splash.route){
                    SplashScreen(navController)
                }
                composable(Screen.Onboarding1.route){
                    OnboardingScreen(navController, 0)
                }
                composable(Screen.Onboarding2.route){
                    OnboardingScreen(navController, 1)
                }
                composable(Screen.Onboarding3.route){
                    OnboardingScreen(navController, 2)
                }
                composable(Screen.Home.route){
                    HomeScreen()
                }
            }
        }
    }
}
