package com.example.myapplication.models

// models/ApiService.kt

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines") // Adjust the endpoint as needed
    suspend fun getNews(
        @Query("sources") source: String = "bbc-news",
        @Query("apiKey") apiKey: String = "YOUR_API_KEY" // Replace with your actual API key
    ): Response<NewsResponse>
}