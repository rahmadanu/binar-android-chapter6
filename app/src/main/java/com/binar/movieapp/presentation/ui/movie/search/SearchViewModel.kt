package com.binar.movieapp.presentation.ui.movie.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.movieapp.data.network.model.search.SearchItem
import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MovieRepository): ViewModel() {

    private val _searchResult = MutableLiveData<Resource<List<SearchItem>>>()
    val searchResult: LiveData<Resource<List<SearchItem>>> = _searchResult

    fun searchMovie(query: String) {
        Log.d("searchMovie", "searchMovie")
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.searchMovie(query)
            viewModelScope.launch(Dispatchers.Main) {
                _searchResult.postValue(data)
            }
        }
    }
}