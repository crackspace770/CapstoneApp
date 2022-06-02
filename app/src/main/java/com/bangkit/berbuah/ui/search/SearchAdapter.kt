package com.bangkit.berbuah.ui.search

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.berbuah.databinding.FragmentSearchBinding
import com.bangkit.berbuah.databinding.SearchItemListBinding
import com.bumptech.glide.Glide
import com.bangkit.berbuah.ui.detail.DetailActivity

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    private var oldStoryItem = emptyList<ListFruitItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldStoryItem[position])
    }

    override fun getItemCount(): Int = oldStoryItem.size

    fun setData(newStoryItem: List<ListFruitItem>) {
        val diffUtil = SearchDiffUtil(oldStoryItem, newStoryItem)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldStoryItem = newStoryItem
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: SearchItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListFruitItem){
            binding.apply {
                binding.apply {
                    tvName.setText(data.nama)
                    Glide.with(itemView.context)
                        .load(data.photo)
                        .centerCrop()
                        .into(imgPhoto)
                    val listListStoryItemDetail = ListFruitItem(data.nama,data.description,data.gizi, data.manfaat,data.photo)
                    Log.d("story:",listListStoryItemDetail.toString())

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_STORY, data)

                        val optionsCompat: ActivityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                Pair(imgPhoto,"imageListStoryItem"),
                                Pair(tvName,"nameListStoryItem"),

                            )
                        itemView.context.startActivity(intent, optionsCompat.toBundle())
                    }
                }

            }
        }
    }

}