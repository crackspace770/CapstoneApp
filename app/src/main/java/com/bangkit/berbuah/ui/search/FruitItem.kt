package com.bangkit.berbuah.ui.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class FruitItem {
    @Parcelize
    data class UserProfile(
        var nama: String? = null,
        var description: String? = null,
        var gizi: String? = null,
        var manfaat: String?= null,
        var photo: String? = null,


    ): Parcelable
}