package com.example.dailynews.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dailynews.model.News
import com.example.dailynews.network.NewsApi
import com.example.dailynews.repositories.NewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


sealed class NewsUIState {
    object Loading : NewsUIState()
    data class Success(val news: News) : NewsUIState()
    data class Error(val message: String) : NewsUIState()

}

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsUIState = MutableLiveData<NewsUIState>()
    val newsUIState: LiveData<NewsUIState>
        get() = _newsUIState

    fun fetchNews() {
        viewModelScope.launch {
            _newsUIState.value = NewsUIState.Loading
            try {
                val results = viewModelScope.async {
                    repository.fetchAllNews()
                }.await()
                _newsUIState.value = NewsUIState.Success(results)

            } catch (e: Exception) {
                _newsUIState.value = NewsUIState.Error(e.message ?: "Unknown Error Occurred")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return NewsViewModel(NewsRepository(NewsApi())) as T
            }
        }
    }
}