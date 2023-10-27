package a77777_888.me.t.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favourites")
    fun getFavouritesList(): Flow<List<VacancyRoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavourite(vacancy: VacancyRoomEntity)

    @Delete
    fun deleteFavourite(vacancy: VacancyRoomEntity)
}