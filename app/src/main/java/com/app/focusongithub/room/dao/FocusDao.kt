package com.app.focusongithub.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.focusongithub.room.entity.BookmarkedUsers

@Dao
interface FocusDao {

    @Query("Select * from bookmarkedUsers")
    fun getAllBookmarkedUsers(): List<BookmarkedUsers>

    @Insert
    fun bookmarkThisUser(userToBookmark: BookmarkedUsers)

    @Delete
    fun unbookmarkThisUser(userToBookmark: BookmarkedUsers)

}