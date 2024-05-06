package com.dicoding.fundamentalfirstsubmission1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.fundamentalfirstsubmission1.databinding.ItemRowBinding
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser

class FollowingAdapter(private val listFollowing: List<GithubUser>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val following = listFollowing[position]

        with(holder.binding) {
            com.bumptech.glide.Glide.with(root.context)
                .load(following.avatarUrl)
                .circleCrop()
                .into(imgUserAvatar)
            tvName.text = following.login
            tvUrl.text = following.htmlUrl
        }
    }

    override fun getItemCount(): Int = listFollowing.size
}