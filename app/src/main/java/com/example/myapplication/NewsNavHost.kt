package com.example.myapplication

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.NewsContent
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.myapplication.screens.ProfileScreen


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NewsNavHost(navController: NavHostController, viewModel: NewsViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable("detail") {
            val selectedArticle = viewModel.selectedArticle.value
            selectedArticle?.let {
                NewsContent(article = it)
            }
        }
        composable("profile"){
            ProfileScreen(navController = navController)
        }
    }

}

