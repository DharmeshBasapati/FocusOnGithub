package com.app.focusongithub.room.dao

import androidx.room.*
import com.app.focusongithub.room.entity.BookmarkedUsers

@Dao
interface FocusDao {

    @Query("Select * from bookmarkedUsers")
    fun getAllBookmarkedUsers(): List<BookmarkedUsers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bookmarkThisUser(userToBookmark: BookmarkedUsers)

    @Delete
    fun unbookmarkThisUser(userToBookmark: BookmarkedUsers)

}