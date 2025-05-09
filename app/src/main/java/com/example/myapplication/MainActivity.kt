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