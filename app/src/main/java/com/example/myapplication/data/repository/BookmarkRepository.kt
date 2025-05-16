// app/src/main/java/com/example/myapplication/data/repository/BookmarkRepository.kt
package com.example.myapplication.data.repository

import com.example.myapplication.data.database.BookmarkDao
import com.example.myapplication.data.database.BookmarkEntity
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    fun getAllBookmarks(): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getAllBookmarks()
    }

    fun isBookmarked(url: String): Flow<Boolean> {
        return bookmarkDao.isBookmarked(url)
    }

    suspend fun addBookmark(article: Article) {
        val bookmarkEntity = BookmarkEntity(
            url = article.url ?: "",
            title = article.title ?: "",
            description = article.description,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            source = article.source?.name,
            content = article.content
        )
        bookmarkDao.insertBookmark(bookmarkEntity)
    }

    suspend fun removeBookmark(url: String) {
        bookmarkDao.deleteBookmarkByUrl(url)
    }
}