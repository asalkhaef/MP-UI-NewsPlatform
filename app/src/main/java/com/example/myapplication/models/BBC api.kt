package com.example.myapplication.screens

// models/News.kt

data class NewsResponse(
    val articles: List<NewsArticle>
)

data class NewsArticle(
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: String, // Or use a proper Date/Time type
    // ... other fields
)