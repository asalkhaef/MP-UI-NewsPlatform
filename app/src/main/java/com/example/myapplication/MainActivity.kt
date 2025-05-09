package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private lateinit var auth: FirebaseAuth
    private var isLoggedIn by mutableStateOf(false)

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

                if (isLoggedIn) {
                    HomeScreen()
                } else {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                }
            }
        }
    }
}
