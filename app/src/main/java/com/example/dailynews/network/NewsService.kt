package com.example.dailynews.network

import com.example.dailynews.model.News
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {
    @GET("https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=")
    suspend fun getMostPopularNews():Response<News>
}