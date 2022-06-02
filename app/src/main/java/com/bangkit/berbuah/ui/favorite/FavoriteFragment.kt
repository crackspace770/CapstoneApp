package com.bangkit.berbuah.ui.favorite

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.berbuah.R
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.databinding.FragmentFavoriteBinding
import com.bangkit.berbuah.ui.detail.DetailActivity
import com.bangkit.berbuah.ui.search.FruitItem

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.rvResult.layoutManager = LinearLayoutManager(context)
        val root: View = binding.root
        return root

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FruitItem) {
                showSelectedUser(data, favorite = Favorite())
            }
        })
    }

    private fun showSelectedUser(user: FruitItem, favorite: Favorite) {
        val moveWithObjectIntent = Intent(this@FavoriteFragment, DetailActivity::class.java)
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_USER, user)
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_FAVORITE, favorite)
        startActivity(moveWithObjectIntent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            (R.id.logout) -> {
                viewModel.logout()
                preferences.edit().apply {
                    clear()
                    apply()
                    finish()
                }
                return true
            }

            else -> false
        }
    }
}