package com.bangkit.berbuah.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.berbuah.api.ApiConfig
import com.bangkit.berbuah.model.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val pref : UserPreference) : ViewModel() {

    private val _listUser = MutableLiveData<List<ListFruitItem>>()
    val listUser: LiveData<List<ListFruitItem>> = _listUser

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("")
    }

    fun findUser(query: String) {
       // _isLoading.value = true
        val client = ApiConfig.getApiService().getListSearch(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
               // _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
            //    _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}