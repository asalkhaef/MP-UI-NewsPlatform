package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.LoginScreen
import androidx.compose.runtime.*



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                var isLoggedIn by
                remember { mutableStateOf(false) } // وضعیت لاگین

                if (isLoggedIn) {
                    HomeScreen() // نمایش صفحه اصلی
                } else {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true }) // مقداردهی onLoginSuccess
                }
            }
        }
    }
}
