package com.example.myapplication.repository

import com.example.myapplication.data.api.RetrofitInstance
import com.example.newsapp.data.model.Article

class NewsRepository {
    suspend fun getTopNews(apiKey: String): List<Article> {
        return RetrofitInstance.api.getTopHeadlines(apiKey = apiKey).articles
    }
}