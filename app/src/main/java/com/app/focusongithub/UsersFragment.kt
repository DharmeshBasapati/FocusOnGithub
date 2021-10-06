package com.app.focusongithub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusongithub.adapter.UsersAdapter
import com.app.focusongithub.base.ViewModelFactory
import com.app.focusongithub.databinding.FragmentUsersBinding
import com.app.focusongithub.model.Users
import com.app.focusongithub.room.builder.DatabaseBuilder
import com.app.focusongithub.room.entity.BookmarkedUsers
import com.app.focusongithub.utils.Status
import com.app.focusongithub.viewmodel.UsersViewModel

class UsersFragment : Fragment() {

    private lateinit var usersAdapter: UsersAdapter
    private lateinit var binding: FragmentUsersBinding
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUsersBinding.inflate(layoutInflater)

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

        usersAdapter = UsersAdapter(arrayListOf()) { pos ->
            addOrRemoveUserFromBookmarks(pos)
        }

        binding.rvUsers.adapter = usersAdapter

    }

    private fun addOrRemoveUserFromBookmarks(pos: Int) {

        if (!usersAdapter.getLatestList()[pos].isBookmarked) {
            usersViewModel.bookmarkThisUser(
                BookmarkedUsers(
                    usersAdapter.getLatestList()[pos].id,
                    usersAdapter.getLatestList()[pos].login,
                    usersAdapter.getLatestList()[pos].avatar_url
                )
            )
            usersAdapter.getLatestList()[pos].isBookmarked = true
        } else {
            usersViewModel.unBookmarkThisUser(
                BookmarkedUsers(
                    usersAdapter.getLatestList()[pos].id,
                    usersAdapter.getLatestList()[pos].login,
                    usersAdapter.getLatestList()[pos].avatar_url
                )
            )
            usersAdapter.getLatestList()[pos].isBookmarked = false
        }

        usersAdapter.notifyItemChanged(pos)

    }

    private fun setupViewModel() {

        usersViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
                )
            ).get(UsersViewModel::class.java)

    }

    private fun setupObserver() {

        usersViewModel.getUsers().observe(requireActivity(), {
            when (it.status) {

                Status.SUCCESS -> {
                    binding.rvUsers.visibility = View.VISIBLE
                    //binding.progressBar.visibility = View.GONE
                    it.data?.let { it1 -> updateUsersList(it1) }
                }
                Status.ERROR -> {
                    binding.rvUsers.visibility = View.GONE
                    //binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.rvUsers.visibility = View.GONE
                    //binding.progressBar.visibility = View.GONE
                }

            }
        })

    }

    private fun updateUsersList(normalUsersList: List<Users>) {
        usersAdapter.addUsers(normalUsersList)
    }

}