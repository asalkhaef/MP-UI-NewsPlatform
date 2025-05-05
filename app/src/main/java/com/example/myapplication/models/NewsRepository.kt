package com.example.myapplication.models

// models/NewsRepository.kt

class NewsRepository(private val apiService: ApiService) {
    suspend fun getNews(): NewsResponse? {
        val response = apiService.getNews()
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Handle error scenarios here (e.g., log, return null, throw exception)
            null
        }
    }
}