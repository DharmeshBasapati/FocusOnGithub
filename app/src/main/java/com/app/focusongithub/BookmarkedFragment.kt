package com.app.focusongithub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusongithub.adapter.BookmarkedAdapter
import com.app.focusongithub.adapter.UsersAdapter
import com.app.focusongithub.base.ViewModelFactory
import com.app.focusongithub.databinding.FragmentBookmarkedBinding
import com.app.focusongithub.room.builder.DatabaseBuilder
import com.app.focusongithub.room.entity.BookmarkedUsers
import com.app.focusongithub.viewmodel.UsersViewModel

class BookmarkedFragment : Fragment() {

    private lateinit var bookmarkedAdapter: BookmarkedAdapter
    private lateinit var binding: FragmentBookmarkedBinding
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkedBinding.inflate(layoutInflater)

        setupUI()

        setupViewModel()

        setupObserver()

        return binding.root
    }

    private fun setupUI() {

        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())

        binding.rvUsers.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        bookmarkedAdapter = BookmarkedAdapter(arrayListOf()){ userToUnBookMark -> unBookMarkUser(userToUnBookMark)

        }

        binding.rvUsers.adapter = bookmarkedAdapter

    }

    private fun unBookMarkUser(userToUnBookMark: BookmarkedUsers) {

        usersViewModel.unBookmarkThisUser(userToUnBookMark)

    }

    private fun setupViewModel() {

        usersViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
                )
            ).get(
                UsersViewModel::class.java
            )

        usersViewModel.fetchBookmarkedUsersFromDB()

    }

    private fun setupObserver() {

        usersViewModel.getBookMarkedUsers().observe(requireActivity(), {
            updateUsersList(it)
        })

    }

    private fun updateUsersList(it1: List<BookmarkedUsers>) {
        bookmarkedAdapter.addUsers(it1)
    }

}