package a77777_888.me.t.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VacancyRoomEntity::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun getFavouritesDao(): FavoritesDao
}