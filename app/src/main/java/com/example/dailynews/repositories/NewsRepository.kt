package com.example.dailynews.repositories

import com.example.dailynews.model.News
import com.example.dailynews.network.NewsApi

class NewsRepository(private val api: NewsApi): NewsRepositoryInt {
    override suspend fun fetchAllNews(): News {
        try {
            return api.getApiNews().getMostPopularNews().body()!!
        }catch (e:Exception){
            throw e
        }
    }
}