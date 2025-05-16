// app/src/main/java/com/example/myapplication/data/database/BookmarkEntity.kt
package com.example.myapplication.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val source: String?,
    val content: String?,
    val bookmarkedAt: Long = System.currentTimeMillis()
)