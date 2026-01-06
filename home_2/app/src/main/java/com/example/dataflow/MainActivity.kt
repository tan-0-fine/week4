package com.example.dataflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

sealed class Screen(val route: String) {

    object Forgot : Screen("forgot")

    object ForgotResult :
        Screen("forgot?email={email}&otp={otp}&password={password}") {
        fun createRoute(email: String, otp: String, password: String) =
            "forgot?email=$email&otp=$otp&password=$password"
    }

    object Verify : Screen("verify/{email}") {
        fun createRoute(email: String) = "verify/$email"
    }

    object Reset : Screen("reset/{email}/{otp}") {
        fun createRoute(email: String, otp: String) =
            "reset/$email/$otp"
    }

    object Confirm : Screen("confirm/{email}/{otp}/{password}") {
        fun createRoute(email: String, otp: String, password: String) =
            "confirm/$email/$otp/$password"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.Forgot.route
            ) {

                // Forgot (lúc mở app)
                composable(Screen.Forgot.route) {
                    ForgotScreen(navController)
                }

                // Forgot (sau khi Submit, có result)
                composable(
                    route = Screen.ForgotResult.route,
                    arguments = listOf(
                        navArgument("email") {
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument("otp") {
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument("password") {
                            type = NavType.StringType
                            nullable = true
                        }
                    )
                ) { backStackEntry ->

                    ForgotScreen(
                        navController = navController,
                        emailResult = backStackEntry.arguments?.getString("email"),
                        otpResult = backStackEntry.arguments?.getString("otp"),
                        passwordResult = backStackEntry.arguments?.getString("password")
                    )
                }

                composable(Screen.Verify.route) {
                    val email = it.arguments?.getString("email") ?: ""
                    VerifyScreen(navController, email)
                }

                composable(Screen.Reset.route) {
                    val email = it.arguments?.getString("email") ?: ""
                    val otp = it.arguments?.getString("otp") ?: ""
                    ResetPasswordScreen(navController, email, otp)
                }

                composable(Screen.Confirm.route) {
                    val email = it.arguments?.getString("email") ?: ""
                    val otp = it.arguments?.getString("otp") ?: ""
                    val password = it.arguments?.getString("password") ?: ""
                    ConfirmScreen(navController, email, otp, password)
                }
            }
        }
    }
}
@Composable
fun ForgotScreen(
    navController: NavController,
    emailResult: String? = null,
    otpResult: String? = null,
    passwordResult: String? = null
) {
    var email by remember { mutableStateOf("") }

    val showResult =
        emailResult != null && otpResult != null && passwordResult != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AppHeader()

        Spacer(Modifier.height(32.dp))

        Text("Forgot Password", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("Enter your email, we will send you a verification code")



        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(
                    Screen.Verify.createRoute(email)
                )
            }
        ) {
            Text("Next")
        }

        if (showResult) {
            Spacer(Modifier.height(32.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            Text("Result:", fontWeight = FontWeight.Bold)
            Text("Email: $emailResult")
            Text("OTP: $otpResult")
            Text("Password: $passwordResult")
        }
    }
}
@Composable
fun VerifyScreen(navController: NavController, email: String) {
    var otp by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AppHeader()
        Spacer(Modifier.height(32.dp))

        Text("Verify Code", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(16.dp))
        Text("Enter the code", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("We just send your register email", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it.take(6) },
            label = { Text("OTP") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(
                    Screen.Reset.createRoute(email, otp)
                )
            }
        ) {
            Text("Next")
        }
    }
}

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    email: String,
    otp: String
) {
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader()
        Spacer(Modifier.height(32.dp))

        Text("Create New Password", fontSize = 18.sp, fontWeight = FontWeight.Medium)

        Spacer(Modifier.height(16.dp))
        Text("Your new password must be different from\n previous used passwords", fontSize = 18.sp, fontWeight = FontWeight.Medium)

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (password == confirm) {
                    navController.navigate(
                        Screen.Confirm.createRoute(email, otp, password)
                    )
                }
            }
        ) {
            Text("Next")
        }
    }
}

@Composable
fun ConfirmScreen(
    navController: NavController,
    email: String,
    otp: String,
    password: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader()
        Spacer(Modifier.height(32.dp))

        Text("Confirm", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(16.dp))
        Text("We are here to help you", fontSize = 18.sp, fontWeight = FontWeight.Medium)

        Spacer(Modifier.height(16.dp))

        InfoBox("Email", email)
        InfoBox("OTP", otp)
        InfoBox("Password", password)


        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(
                    Screen.ForgotResult.createRoute(email, otp, password)
                )
            }
        ) {
            Text("Submit")
        }
    }
}
@Composable
fun AppHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Image(
            painter = painterResource(id = R.drawable.uth),
            contentDescription = "UTH Logo",
            modifier = Modifier.height(80.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "SmartTasks",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )
    }
}
@Composable
fun InfoBox(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Text(label, fontSize = 13.sp, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}
