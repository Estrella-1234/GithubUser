package com.dicoding.fundamentalfirstsubmission1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fundamentalfirstsubmission1.R
import com.dicoding.fundamentalfirstsubmission1.adapter.MainAdapter
import com.dicoding.fundamentalfirstsubmission1.databinding.ActivityMainBinding
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser
import com.dicoding.fundamentalfirstsubmission1.theme.SettingPref
import com.dicoding.fundamentalfirstsubmission1.utils.loading
import com.dicoding.fundamentalfirstsubmission1.viewmodel.MainViewModel
import com.dicoding.fundamentalfirstsubmission1.viewmodel.SettingViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()
    private val listGithubUser = ArrayList<GithubUser>()
    private val load = loading()

    private val settingViewModel by viewModels<SettingViewModel> {
        SettingViewModel.Setting(SettingPref(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchView()

        mainViewModel.listGithubUser.observe(this) { listGithubUser ->
            setUserData(listGithubUser)
        }

        mainViewModel.isLoading.observe(this) {
            load.showLoading(it, binding.progressBar)
        }

        mainViewModel.totalCount.observe(this) {
            showText(it)
        }

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUser.layoutManager = layoutManager

        changeTheme()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.mnfavorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setUserData(listGithubUser: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        for (user in listGithubUser) {
            listUser.clear()
            listUser.addAll(listGithubUser)
        }
        val adapter = MainAdapter(listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            val tvNotice = binding.tvNotice
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.rvUser.visibility = View.VISIBLE
                    tvNotice.visibility = View.GONE
                    mainViewModel.searchGithubUser(it)
                    setUserData(listGithubUser)
                }
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showSelectedUser(data: GithubUser) {
        val moveWithParcelableIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
        moveWithParcelableIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
        startActivity(moveWithParcelableIntent)
    }

    private fun showText(totalCount: Int) {
        with(binding) {
            if (totalCount == 0) {
                tvNotice.visibility = View.VISIBLE
                tvNotice.text = resources.getString(R.string.user_not_found)
            } else {
                tvNotice.visibility = View.INVISIBLE
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.rvUser.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun changeTheme() {
        settingViewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}