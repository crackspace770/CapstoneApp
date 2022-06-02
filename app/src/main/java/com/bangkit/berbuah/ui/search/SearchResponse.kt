package com.bangkit.berbuah.ui.search

import com.google.gson.annotations.SerializedName

data class SearchResponse (

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<ListFruitItem>
)

data class ListFruitItem(

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("deskripsi")
    val description: String,

    @field:SerializedName("gizi")
    val gizi: String,

    @field:SerializedName("manfaat")
    val manfaat: String,

    @field:SerializedName("photo")
    val photo: String,

    )