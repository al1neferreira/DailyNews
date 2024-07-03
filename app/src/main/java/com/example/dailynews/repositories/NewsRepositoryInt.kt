package com.example.dailynews.repositories

import com.example.dailynews.model.News

interface NewsRepositoryInt {
    suspend fun fetchAllNews(): News
}