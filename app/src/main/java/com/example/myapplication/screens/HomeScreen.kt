package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ViewModels.BookmarkViewModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.viewmodel.NewsViewModel



data class BottomNavigationItem(
    val title: String,
    val icon: Int
)


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen(viewModel: NewsViewModel, navController: NavController) {
    val articles by viewModel.articles.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        topBar = { CustomTopAppBar() },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                item { SearchBar() }
                item { TrendingSection() }
                items(articles) { article ->
                    NewsItemCard(article, navController)
                }
            }
        }
    )
}

@Composable
fun NewsItemCard(article: Article, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                navController.navigate("detail") },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_pic),
                error = painterResource(R.drawable.alert_pic)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = article.title ?: "No title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.description ?: "No description",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            title = "Profile",
            icon = R.drawable.profile
        ),
        BottomNavigationItem(
            title = "Home",
            icon = R.drawable.home_filled
        ),
        BottomNavigationItem(
            title = "Bookmark",
            icon = R.drawable.bookmark
        )
    )

    NavigationBar(
        containerColor = Color.Transparent
    ) {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = { when (item.title) {
                    "Profile" -> navController.navigate("profile")
                    "Home" -> navController.navigate("home")
                    "Bookmark" -> navController.navigate("bookmarks")
                }},
                icon = {
                    if (item.title == "Home") {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (item.title == "Home") MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}


@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(56.dp)
            .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "search_icon",
            modifier = Modifier
                .padding(start = 16.dp, end = 0.dp)
                .size(24.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search", color = Color.LightGray) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Yellow,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar() {
    TopAppBar(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 28.dp),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.main_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            )
        },
        title = {
            Text(text = "", style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            NotificationIcon()
        }
    )
}

@Composable
fun NotificationIcon() {
    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.notif_icon),
            contentDescription = "notification_icon",
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun TrendingSection() {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Text(
                text = "Trending",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 10.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.pic_new_trending),
                contentDescription = "news_image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
    }
}


// Add this to NewsItemCard in HomeScreen.kt
@Composable
fun NewsItemCard(
    article: Article,
    navController: NavController,
    viewModel: BookmarkViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isBookmarked by viewModel.isBookmarked(article.url ?: "").collectAsState(initial = false)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                navController.navigate("detail")
            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_pic),
                error = painterResource(R.drawable.alert_pic)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title ?: "No title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.description ?: "No description",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = "Bookmark",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { viewModel.toggleBookmark(article) },
                tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}

