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
import com.bumptech.glide.Glide
import com.bangkit.berbuah.ui.detail.DetailActivity

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    private var oldStoryItem = emptyList<ListStoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldStoryItem[position])
    }

    override fun getItemCount(): Int = oldStoryItem.size

    fun setData(newStoryItem: List<ListStoryItem>) {
        val diffUtil = SearchDiffUtil(oldStoryItem, newStoryItem)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldStoryItem = newStoryItem
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: FragmentSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem){
            binding.apply {
                val dateList = data.createdAt.split("T")
                val dateListListStoryItem = dateList[0]
                binding.apply {
                    tvName.setText(data.name)
                    tvDescription.setText(data.description)
                    Glide.with(itemView.context)
                        .load(data.photoUrl)
                        .centerCrop()
                        .into(imgAvatar)
                    tvDate.setText(dateListListStoryItem)
                    val listListStoryItemDetail = ListStoryItem(data.name,data.description,data.photoUrl)
                    Log.d("story:",listListStoryItemDetail.toString())

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_STORY, data)

                        val optionsCompat: ActivityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                Pair(imgAvatar,"imageListStoryItem"),
                                Pair(tvName,"nameListStoryItem"),
                                Pair(tvDescription,"nameListStoryItem"),
                                Pair(tvDate,"dateListStoryItem"),
                            )
                        itemView.context.startActivity(intent, optionsCompat.toBundle())
                    }
                }

            }
        }
    }

}