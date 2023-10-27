package a77777_888.me.t.domain.usecases

import a77777_888.me.t.domain.dataentities.IFavorites
import a77777_888.me.t.domain.dataentities.IVacanciesListItem
import kotlinx.coroutines.flow.Flow

class FavoritesInterActor(private val iFavorites: IFavorites) {
    fun getFavorites(): Flow<List<IVacanciesListItem>> = iFavorites.getAll()
    suspend fun addFavorite(item: IVacanciesListItem) = iFavorites.addItem(item)
    suspend fun deleteFavorite(item: IVacanciesListItem) = iFavorites.deleteItem(item)
}