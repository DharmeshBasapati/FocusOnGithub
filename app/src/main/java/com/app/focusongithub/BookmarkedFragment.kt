package com.app.focusongithub

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusongithub.adapter.BookmarkedAdapter
import com.app.focusongithub.base.ViewModelFactory
import com.app.focusongithub.databinding.FragmentBookmarkedBinding
import com.app.focusongithub.room.builder.DatabaseBuilder
import com.app.focusongithub.room.entity.BookmarkedUsers
import com.app.focusongithub.viewmodel.UsersViewModel

class BookmarkedFragment : Fragment() {

    private lateinit var bookmarkedUsersList: List<BookmarkedUsers>
    private lateinit var bookmarkedAdapter: BookmarkedAdapter
    private lateinit var binding: FragmentBookmarkedBinding
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentBookmarkedBinding.inflate(layoutInflater)

        setupUI()

        setupViewModel()

        setupObserver()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)

        val mSearch = menu.findItem(R.id.search)
        val mSearchView = mSearch.actionView as SearchView

        mSearchView.isIconified = false
        mSearchView.queryHint = "Search User..."

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.d("TAG", "onQueryTextSubmit: $p0")
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("TAG", "onQueryTextChange: $p0")
                if (p0 != null) {
                    doSearchUser(p0)
                }
                return true
            }

        })
    }

    private fun doSearchUser(searchTerm: String) {

        if (searchTerm.isNotEmpty()) {

            val tempBookMarkedList = bookmarkedUsersList

            val updatedList = tempBookMarkedList.toMutableList()
                .filter { it.login.contains(searchTerm, false) }

            updateSearchUsersList(updatedList)

        } else {

            updateUsersList(bookmarkedUsersList)

        }


    }

    private fun setupUI() {

        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())

        binding.rvUsers.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        bookmarkedAdapter = BookmarkedAdapter(arrayListOf()) { userToUnBookMark ->
            unBookMarkUser(userToUnBookMark)
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
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext)
                        .focusDao()
                )
            ).get(UsersViewModel::class.java)

        usersViewModel.fetchBookmarkedUsersFromDB()

    }

    private fun setupObserver() {

        usersViewModel.getBookMarkedUsers().observe(requireActivity(), {
            updateUsersList(it)
        })

    }

    private fun updateUsersList(it1: List<BookmarkedUsers>) {
        bookmarkedUsersList = it1
        bookmarkedAdapter.addUsers(it1)
    }

    private fun updateSearchUsersList(it1: List<BookmarkedUsers>) {
        bookmarkedAdapter.addUsers(it1)
    }


}