package com.dicoding.fundamentalfirstsubmission1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fundamentalfirstsubmission1.R
import com.dicoding.fundamentalfirstsubmission1.adapter.FavoriteAdapter
import com.dicoding.fundamentalfirstsubmission1.databinding.ActivityFavoriteBinding
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser
import com.dicoding.fundamentalfirstsubmission1.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = "Favorites"
        supportActionBar?.title = title

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


        favoriteViewModel.getFavorite()?.observe(this) { GithubUser ->
            setUserData(GithubUser)
        }

        favoriteViewModel.getFavorite()?.observe(this) { GithubUser ->
            showText(GithubUser)
        }


        val layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvFavorite.layoutManager = layoutManager


    }

    private fun showText(favoriteUsers: List<GithubUser>) {
        val totalCount = favoriteUsers.size
        with(binding) {
            if (totalCount == 0) {
                tvNoticeFavorite.visibility = View.VISIBLE
                tvNoticeFavorite.text = resources.getString(R.string.no_favorite)
            } else {
                tvNoticeFavorite.visibility = View.INVISIBLE
            }
        }
    }

    private fun setUserData(favoriteUsers: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        for (user in favoriteUsers) {
            listUser.clear()
            listUser.addAll(favoriteUsers)
        }
        Log.d("Data", "Data: $listUser")
        val adapter = FavoriteAdapter(listUser)
        binding.rvFavorite.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)

            }
        })
    }


    private fun showSelectedUser(data: GithubUser) {
        val moveWithParcelableIntent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
        moveWithParcelableIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
        startActivity(moveWithParcelableIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}