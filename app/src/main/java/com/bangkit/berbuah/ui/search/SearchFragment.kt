package com.bangkit.berbuah.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.berbuah.R

import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.databinding.FragmentSearchBinding

import java.util.ArrayList


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var adapter: SearchAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvResult.layoutManager = LinearLayoutManager(context)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.btnSend.setOnClickListener { view ->
            mainViewModel.findUser(binding.edSearch.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setSearchData(searchResult: List<SearchItem>) {
        val listUser: ArrayList<UserProfile> = ArrayList()
        for (user in searchResult) {
            val userList = UserProfile(user.name, user.photo, favorite?.isFavorite)
            listUser.add(userList)
        }
        val adapter = SearchAdapter(listUser)
        binding.rvResult.adapter = adapter
        binding.edSearch.setText("")
        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserProfile) {
                showSelectedUser(data, favorite = Favorite())
            }
        })
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