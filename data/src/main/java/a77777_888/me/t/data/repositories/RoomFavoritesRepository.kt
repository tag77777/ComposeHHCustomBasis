package a77777_888.me.t.data.repositories

import a77777_888.me.t.data.local.FavoritesDao
import a77777_888.me.t.data.local.VacancyRoomEntity
import a77777_888.me.t.domain.dataentities.IFavorites
import a77777_888.me.t.domain.dataentities.IVacanciesListItem
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomFavoritesRepository @Inject constructor(
    private val favoritesDao: FavoritesDao
) : IFavorites {
    override fun getAll(): Flow<List<IVacanciesListItem>> =
        favoritesDao.getFavouritesList().map {
            it.map { vacancyRoomEntity -> VacanciesListItem(vacancyRoomEntity) }
        }

    override suspend fun addItem(item: IVacanciesListItem) {
        val vacancyEntity = VacancyRoomEntity(item)
        favoritesDao.addFavourite(vacancyEntity)
    }

    override suspend fun deleteItem(item: IVacanciesListItem) {
        val vacancyEntity = VacancyRoomEntity(item)
        favoritesDao.deleteFavourite(vacancyEntity)
    }
}