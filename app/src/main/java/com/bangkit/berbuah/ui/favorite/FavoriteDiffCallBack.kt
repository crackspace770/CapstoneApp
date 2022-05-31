package com.bangkit.berbuah.ui.favorite

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.berbuah.database.Favorite


class FavoriteDiffCallBack(private val mOldFavoriteList: List<Favorite>, private val mNewFavoriteList: List<Favorite>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].name == mNewFavoriteList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = mOldFavoriteList[oldItemPosition]
        val newData = mNewFavoriteList[newItemPosition]
        return oldData.name == newData.name && oldData.photo == newData.photo && oldData.isFavorite == newData.isFavorite
    }




}