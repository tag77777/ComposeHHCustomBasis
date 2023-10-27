package a77777_888.me.t.domain.dataentities

import kotlinx.coroutines.flow.Flow

interface IFavorites {
    fun getAll(): Flow<List<IVacanciesListItem>>
    suspend fun addItem(item: IVacanciesListItem)
    suspend fun deleteItem(item: IVacanciesListItem)
}