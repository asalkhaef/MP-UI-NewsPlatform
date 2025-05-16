// app/src/main/java/com/example/myapplication/viewmodel/BookmarkViewModel.kt
package com.example.myapplication.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.repository.BookmarkRepository
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BookmarkRepository

    init {
        val bookmarkDao = AppDatabase.getDatabase(application).bookmarkDao()
        repository = BookmarkRepository(bookmarkDao)
    }

    fun getAllBookmarks(): Flow<List<Article>> {
        return repository.getAllBookmarks().map { bookmarkList ->
            bookmarkList.map { bookmark ->
                Article(
                    url = bookmark.url,
                    title = bookmark.title,
                    description = bookmark.description,
                    urlToImage = bookmark.urlToImage,
                    publishedAt = bookmark.publishedAt,
                    source = Source(id = null, name = bookmark.source.toString()),
                    content = bookmark.content,
                    author = "Unknown"
                )
            }
        }
    }

    fun isBookmarked(url: String): Flow<Boolean> {
        return repository.isBookmarked(url)
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val url = article.url ?: return@launch
            val isBookmarked = repository.isBookmarked(url).first() //  به جای collect
            if (isBookmarked) {
                repository.removeBookmark(url)
            } else {
                repository.addBookmark(article)
            }
        }
    }

}

class BookmarkViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

