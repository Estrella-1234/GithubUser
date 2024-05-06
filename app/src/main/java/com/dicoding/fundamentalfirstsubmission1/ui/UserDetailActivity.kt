package com.dicoding.fundamentalfirstsubmission1.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.fundamentalfirstsubmission1.R
import com.dicoding.fundamentalfirstsubmission1.adapter.PagerAdapter
import com.dicoding.fundamentalfirstsubmission1.databinding.ActivityUserDetailBinding
import com.dicoding.fundamentalfirstsubmission1.models.DetailResponse
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser
import com.dicoding.fundamentalfirstsubmission1.utils.loading
import com.dicoding.fundamentalfirstsubmission1.viewmodel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private val Load = loading()

    private lateinit var userDetailViewModel: UserDetailViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDetailViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        userDetailViewModel.listDetail.observe(this) { detailList ->
            setDataToView(detailList)
        }

        userDetailViewModel.isLoading.observe(this) {
            Load.showLoading(it, binding.progressBar2)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = "User Detail"
        supportActionBar?.title = title

        setTabLayoutView()


        val user = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
        val login = user.login
        val id = user.id
        val avatarurl = user.avatarUrl
        val htmlurl = user.htmlUrl


        var isFavorite = false

        CoroutineScope(Dispatchers.IO).launch {
            val count = userDetailViewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if(count>0){
                        binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_red)
                        isFavorite = true
                    }
                    else{
                        binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_outline)
                        isFavorite =false
                    }
                }
            }
        }

        binding.floatingActionButton.setOnClickListener{
            isFavorite = !isFavorite
            if (isFavorite){
                userDetailViewModel.addFavorite(login, id, avatarurl, htmlurl)
                binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_red)
            }
            else{
                userDetailViewModel.removeFavorite(id)
                binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_outline)
            }

        }




    }

    private fun setTabLayoutView() {
        val userIntent = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
        userDetailViewModel.getGithubUser(userIntent.login)

        val login = Bundle()
        login.putString(EXTRA_FRAGMENT, userIntent.login)

        val sectionPagerAdapter = PagerAdapter(this, login)
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun setDataToView(detailList: DetailResponse) {
        binding.apply {
            Glide.with(this@UserDetailActivity)
                .load(detailList.avatarUrl)
                .circleCrop()
                .into(detailsIvAvatar)
            detailsTvName.text = detailList.name ?: "No Name."
            detailsTvUsername.text = detailList.login
            detailsTvFollower.text = resources.getString(R.string.follower, detailList.followers)
            detailsTvFollowing.text = resources.getString(R.string.following, detailList.following)


        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"
    }
}