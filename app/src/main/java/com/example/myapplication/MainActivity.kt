package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.screens.NewsScreen
import com.example.myapplication.screens.ProfileScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

                NavHost(navController = navController, startDestination = if (isLoggedIn) "home" else "login") {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                isLoggedIn = true
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onSignUpClick = {
                                // Handle sign-up navigation if needed
                            }
                        )
                    }
                    composable("home") {
                        val apiKey = "b4aeb6ceab86413d80ade3ca2f0b2c20"
                        val viewModel = NewsViewModel(apiKey)
                        HomeScreen(viewModel, navController)
                    }
                    composable("detail") {
                        NewsScreen(navController)
                    }
                    composable("profile") {
                        ProfileScreen(navController)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, you can navigate to home screen or update UI
        }
    }
}