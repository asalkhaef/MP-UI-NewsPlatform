package com.example.myapplication.models

// screens/NewsViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.NewsArticle
import com.example.myapplication.models.NewsRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val news: List<NewsArticle>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            val newsResponse = repository.getNews()
            if (newsResponse != null) {
                _uiState.value = NewsUiState.Success(newsResponse.articles)
            } else {
                _uiState.value = NewsUiState.Error("Failed to fetch news")
            }
        }
    }
}