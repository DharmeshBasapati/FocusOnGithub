package com.app.focusongithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusongithub.R
import com.app.focusongithub.databinding.RowItemUsersBinding
import com.app.focusongithub.model.Users
import com.bumptech.glide.Glide

class UsersAdapter(private var usersList: List<Users>, val onItemBookmarked: (Int)->Unit) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    inner class UsersViewHolder(val rowItemUsersBinding: RowItemUsersBinding) :
        RecyclerView.ViewHolder(rowItemUsersBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(
        RowItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        with(holder) {
            with(usersList[position]) {

                rowItemUsersBinding.tvUsername.text = login

                Glide.with(itemView.context).load(avatar_url).into(rowItemUsersBinding.ivUsers)

                rowItemUsersBinding.ivBookmark.setImageResource(R.drawable.ic_unbookmarked)

                if (isBookmarked) {
                    rowItemUsersBinding.ivBookmark.setImageResource(R.drawable.ic_bookmarked)
                }

                rowItemUsersBinding.ivBookmark.setOnClickListener {
                    onItemBookmarked(position)
                }

            }
        }
    }

    override fun getItemCount() = usersList.size

    fun addUsers(data: List<Users>) {
        usersList = data
        notifyItemRangeChanged(0, usersList.size)
    }

    fun getLatestList(): List<Users>{
        return usersList
    }
}