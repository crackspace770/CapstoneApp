package com.bangkit.berbuah.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.ui.favorite.FavoriteRepository

class FavoriteUpdateViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }


    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

}