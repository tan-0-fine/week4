package com.example.navigation.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.navigation.navigation.Screen
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource
import com.example.navigation.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Screen.Onboarding1.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.uth),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "UTH SmartTasks",
                color = Color(0xFF1976D2),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}