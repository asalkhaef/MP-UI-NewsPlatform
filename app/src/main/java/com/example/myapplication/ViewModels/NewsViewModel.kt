package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.NewsRepository
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val apiKey: String) : ViewModel() {

    private val repository = NewsRepository()
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                _articles.value = repository.getTopNews(apiKey)
            } catch (e: Exception) {
                // log
                e.printStackTrace()
            }
        }
    }
}
