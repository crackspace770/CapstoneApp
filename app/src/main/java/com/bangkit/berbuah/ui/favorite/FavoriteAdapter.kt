package com.bangkit.berbuah.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.databinding.FragmentFavoriteBinding
import com.bangkit.berbuah.ui.detail.DetailActivity
import com.bangkit.berbuah.ui.search.FruitItem

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()  {

    private val listFavorites = ArrayList<Favorite>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: FruitItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    //menampilkan recyclerView
    inner class FavoriteViewHolder(private val binding: FragmentFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            if (favorite.isFavorite == true) {
                binding.tvName.text = favorite.login
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .into(binding.imgAvatar)
                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    val user = FruitItem(favorite.nama, favorite.photo)
                    intent.putExtra(DetailActivity.EXTRA_FAVORITE, favorite)
                    intent.putExtra(DetailActivity.EXTRA_USER, user)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    //mendapatkan data favorites
    fun setListFavorites(listFavorites: List<Favorite>) {
        val diffCallBack = FavoriteDiffCallBack(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }


}