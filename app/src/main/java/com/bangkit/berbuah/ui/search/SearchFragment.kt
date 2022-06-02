package com.bangkit.berbuah.ui.search

import android.content.Context
import android.content.SharedPreferences
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
import com.bangkit.berbuah.ui.login.LoginActivity

import java.util.ArrayList


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var adapter: SearchAdapter
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        preferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)

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

    private fun setSearchData(searchResult: List<ListFruitItem>) {
        val listFruit: ArrayList<FruitItem> = ArrayList()
        for (fruit in searchResult) {
            val userList = FruitItem(fruit.nama, fruit.photo)
            listFruit.add(userList)
        }
        val adapter = SearchAdapter(listFruit)
        binding.rvResult.adapter = adapter
        binding.edSearch.setText("")

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