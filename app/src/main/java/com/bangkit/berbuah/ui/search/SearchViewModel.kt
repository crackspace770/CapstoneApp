package com.bangkit.berbuah.ui.search

import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("")
    }

    fun findUser(query: String) {

    }
}