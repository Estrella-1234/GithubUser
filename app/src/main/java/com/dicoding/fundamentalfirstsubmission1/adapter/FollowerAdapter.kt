package com.dicoding.fundamentalfirstsubmission1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.fundamentalfirstsubmission1.databinding.ItemRowBinding
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser

class FollowerAdapter(private val listFollower: List<GithubUser>) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listFollower[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(follower.avatarUrl)
                .circleCrop()
                .into(imgUserAvatar)
            tvName.text = follower.login
            tvUrl.text = follower.htmlUrl
        }
    }

    override fun getItemCount(): Int = listFollower.size
}