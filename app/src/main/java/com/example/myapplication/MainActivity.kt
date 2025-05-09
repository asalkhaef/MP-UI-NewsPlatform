package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.screens.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
//                LoginScreen(onLoginSuccess = { isLoggedIn = true })
//               ProfileScreen()
                HomeScreen()
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
