package a77777_888.me.t.composehhcustombasis.di

import a77777_888.me.t.data.remote.RetrofitDataProvider
import a77777_888.me.t.data.repositories.RoomFavoritesRepository
import a77777_888.me.t.data.repositories.SharedPreferencesSearchSettings
import a77777_888.me.t.domain.dataentities.IDataProvider
import a77777_888.me.t.domain.dataentities.IFavorites
import a77777_888.me.t.domain.models.IFlowableSearchSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SettingsModule {
    @Binds
    fun bindSearchSettings(
        settings: SharedPreferencesSearchSettings
    ) : IFlowableSearchSettings
    
    @Binds
    fun bindDataProvider(
        dataProvider: RetrofitDataProvider
    ) : IDataProvider

    @Binds
    fun bindFavoritesRepository(
        repository: RoomFavoritesRepository
    ) : IFavorites
}