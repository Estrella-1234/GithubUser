package com.dicoding.fundamentalfirstsubmission1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fundamentalfirstsubmission1.adapter.FollowerAdapter
import com.dicoding.fundamentalfirstsubmission1.databinding.FragmentFollowerBinding
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser
import com.dicoding.fundamentalfirstsubmission1.utils.loading
import com.dicoding.fundamentalfirstsubmission1.viewmodel.FollowerViewModel


class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var followerViewModel: FollowerViewModel
    private val Load = loading()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowerViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            Load.showLoading(it, binding.progressBar3)
        }
        followerViewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
            setDataToFragment(listFollower)
        }

        followerViewModel.getFollower(arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString())
    }

    private fun setDataToFragment(listFollower: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        with(binding) {
            for (user in listFollower) {
                listUser.clear()
                listUser.addAll(listFollower)
            }
            rvFollower.layoutManager = LinearLayoutManager(context)
            val adapter = FollowerAdapter(listFollower)
            rvFollower.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}