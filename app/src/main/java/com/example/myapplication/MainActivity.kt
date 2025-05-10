package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.screens.*
import com.example.newsapp.viewmodel.NewsViewModel


class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {

      //         ProfileScreen()

                val apiKey = "55d12b83a8914192b99f8841e32133f0"

                setContent {
                    val viewModel = NewsViewModel(apiKey)
                    HomeScreen(viewModel)
                }
               //HomeScreen()
//                var isLoggedIn by
//                remember { mutableStateOf(false) } // وضعیت لاگین
//
//                if (isLoggedIn) {
//                    HomeScreen() // نمایش صفحه اصلی
//                } else {
//                    LoginScreen(onLoginSuccess = { isLoggedIn = true }) // مقداردهی onLoginSuccess
//                }


            }
        }
    }
}
