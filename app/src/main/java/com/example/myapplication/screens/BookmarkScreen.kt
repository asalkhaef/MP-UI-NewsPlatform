// app/src/main/java/com/example/myapplication/screens/BookmarkScreen.kt
package com.example.myapplication.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ViewModels.BookmarkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel, navController: NavController) {
    val bookmarkedArticles by viewModel.getAllBookmarks().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bookmarks",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        if (bookmarkedArticles.isEmpty()) {
            Text(
                text = "No bookmarks yet",
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(bookmarkedArticles) { article ->
                    NewsItemCard(article, navController)
                }
            }
        }
    }
}