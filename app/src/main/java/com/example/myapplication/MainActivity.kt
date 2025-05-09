package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance() // مقداردهی Firebase Auth

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // بررسی وضعیت لاگین
                if (auth.currentUser != null) {
                    HomeScreen()
                } else {
                    LoginScreen()
                }
            }
        }
    }
}