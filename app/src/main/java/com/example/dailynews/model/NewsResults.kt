package com.example.dailynews.model

import com.google.gson.annotations.SerializedName

data class NewsResults(
    val url: String,
    val id: Long,
    @SerializedName("published_at")
    val publishedDate: String,
    val section: String,
    val title: String,
    val media: List<ImageNews>
)
