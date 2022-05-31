package com.bangkit.berbuah.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.berbuah.database.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorite ORDER BY login ASC")
    fun getAllFavorite(): LiveData<List<Favorite>>
}