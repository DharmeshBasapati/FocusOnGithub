package com.app.focusongithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusongithub.R
import com.app.focusongithub.databinding.RowItemUsersBinding
import com.app.focusongithub.room.entity.BookmarkedUsers
import com.bumptech.glide.Glide

class BookmarkedAdapter(
    private var usersList: List<BookmarkedUsers>,
    val onItemUnBookMarked: (BookmarkedUsers) -> Unit
) :
    RecyclerView.Adapter<BookmarkedAdapter.UsersViewHolder>() {
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

                rowItemUsersBinding.ivBookmark.setImageResource(R.drawable.ic_bookmarked)


                rowItemUsersBinding.ivBookmark.setOnClickListener {

                    //Call Remove From Bookmarks Table
                    onItemUnBookMarked(usersList[position])
                }

            }
        }
    }

    override fun getItemCount() = usersList.size

    fun addUsers(data: List<BookmarkedUsers>) {
        usersList = data
        notifyDataSetChanged()
    }
}