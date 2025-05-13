package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.screens.NewsScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var isLoggedIn by mutableStateOf(false)

    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // چک کردن وضعیت اولیه کاربر
        CoroutineScope(Dispatchers.Main).launch {
            auth.currentUser?.let {
                isLoggedIn = true
            }
        }

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                
                if (!isLoggedIn) {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                } else {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            val apiKey = "55d12b83a8914192b99f8841e32133f0"
                            val viewModel = NewsViewModel(apiKey)
                            HomeScreen(viewModel, navController)
                        }
                        composable("detail") {
                            NewsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}