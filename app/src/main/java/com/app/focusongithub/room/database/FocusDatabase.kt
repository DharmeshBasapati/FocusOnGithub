package com.app.focusongithub.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.focusongithub.room.dao.FocusDao
import com.app.focusongithub.room.entity.BookmarkedUsers

@Database(entities = [BookmarkedUsers::class], version = 1)
abstract class FocusDatabase: RoomDatabase() {

    abstract fun focusDao() : FocusDao

}