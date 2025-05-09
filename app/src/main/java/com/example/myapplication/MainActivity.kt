package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.screens.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.ProfileScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {

 //               AppNavigation()

                var loggedInUsername by remember { mutableStateOf<String?>(null) }

                if (loggedInUsername != null) {
                    // اگر کاربر لاگین کرده، برو به صفحه اصلی و نام کاربری را بده
                    AppNavigation(loggedInUsername!!)
                } else {
                    // در غیر این صورت صفحه لاگین نمایش داده شود
                    LoginScreen(onLoginSuccess = { username ->
                        loggedInUsername = username
                    })
            }
        }
    }
}

@Composable
fun AppNavigation(loggedInUsername: String)
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController,loggedInUsername) }
       // composable("login") { LoginScreen(navController) }
        composable("singleNews") { NewsScreen(navController) }
}}}