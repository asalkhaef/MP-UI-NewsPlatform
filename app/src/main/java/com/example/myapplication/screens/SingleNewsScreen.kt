package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.data.model.Article


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavHostController) {

    val article =
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Article>("article")
    var isBookmarked by remember { mutableStateOf(false) }


    if (article == null) {
        Text("No article found")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(
                            painterResource(id = R.drawable.back_icon),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isBookmarked = !isBookmarked }) {
                        Image(
                            painter = painterResource(
                                id = if (isBookmarked) R.drawable.ic_bookmark_filled
                                else R.drawable.ic_bookmark_empty
                            ),
                            contentDescription = "Bookmark",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ){ paddingValues ->
        NewsContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            article = article
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsContent(modifier: Modifier = Modifier, article: Article) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_pic),
                error = painterResource(R.drawable.alert_pic)
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.source.name ?: "Unknown Source",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = article.publishedAt ?: "Unknown Time",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = article.title ?: "No Title",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = article.content ?: article.description ?: "No Content",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewNewsArticle() {
//    NewsScreen()
//}