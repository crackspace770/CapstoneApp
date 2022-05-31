package com.bangkit.berbuah.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (

    @PrimaryKey
    @ColumnInfo(name = "login")
    var name: String = "",

    @ColumnInfo(name = "avatar")
    var photo: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false

): Parcelable