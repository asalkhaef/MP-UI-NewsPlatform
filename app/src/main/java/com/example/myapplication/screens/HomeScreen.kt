package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.models.NewsRepository
import com.example.myapplication.models.RetrofitInstance


data class NewsItem(val title: String, val description: String, val imageUrl: Int)

data class BottomNavigationItem(
    val title: String,
    val icon: Int
)


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen( newsViewModel: NewsViewModel = viewModel(factory = NewsViewModelFactory(
    NewsRepository(RetrofitInstance.api)
))) {

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

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent, // پس‌زمینه سفید


            ){

                bottomNavigationItems.forEach { item ->
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = {
                            if(item.title == "Home"){
                                Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.primary)
                            }else{
                                 Icon(painter = painterResource(id = item.icon),
                                     contentDescription = item.title, tint = MaterialTheme.colorScheme.onSurface)
                                }
                               },
                        label = { Text(text = item.title , color = if(item.title == "Home")MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface) }
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 20.dp , vertical = 28.dp),

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
                    Text(text = "", style = MaterialTheme.typography.titleLarge) // اضافه کردن عنوان
                },
                actions = {
                    Box(
                        modifier = Modifier
//                        .shadow(
//                            elevation = 6.dp,
//                            shape = RoundedCornerShape(8.dp),
//                            ambientColor = Color.LightGray)
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

                },

            )
        },
        content = {
            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(56.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onSurface , RoundedCornerShape(8.dp)), verticalAlignment = Alignment.CenterVertically){
                        Icon(
                            painter = painterResource(id = R.drawable.searcg_icon),
                            contentDescription = "search_icon",
                            modifier = Modifier
                                .padding(start = 16.dp , end = 0.dp)
                                .size(24.dp)
                                , tint = MaterialTheme.colorScheme.onSurface
                        )
                        TextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search", color = Color.LightGray) },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White, // رنگ پس‌زمینه در حالت فوکوس
                                unfocusedContainerColor = Color.White, // رنگ پس‌زمینه در حالت عادی
                                cursorColor = Color.Yellow,
                                focusedIndicatorColor = Color.Transparent, // حذف خط زیرین در حالت فوکوس
                                unfocusedIndicatorColor = Color.Transparent, // حذف خط زیرین در حالت عادی
                                disabledIndicatorColor = Color.Transparent // حذف خط زیرین در حالت غیرفعال
                            )
                        )


                    }
                }
                item {
                    Box(
                        modifier = Modifier.padding(20.dp).fillMaxWidth()

                    ) {
                        Column(
                            modifier = Modifier.background(Color.White, RoundedCornerShape(10.dp)).padding(10.dp)

                        ) {
                            Text(
                                text = "Trending",
                                color = Color.Black,
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                                modifier = Modifier.padding(bottom = 10.dp),
                            )
                            Image(painter = painterResource(id = R.drawable.pic_new_trending),
                                contentDescription = "news_image", modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(20.dp)),
                            )

                        }
                    }
                }

                items(5) { index ->
                    NewsItemCard(
                        newsItem = NewsItem(
                            title = "Europe",
                            description = "Ukraine's President Zelensky to BBC: Blood money being paid...$index",
                            imageUrl = R.drawable.pic_new_trending
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun NewsItemCard(newsItem: NewsItem) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable {
                // Handle click here
            }
            , colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = newsItem.imageUrl),
                contentDescription = "News Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = newsItem.title, fontWeight = FontWeight.Light, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = newsItem.description, fontSize = 12.sp, fontWeight = FontWeight.W500)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

