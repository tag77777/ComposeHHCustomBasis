package a77777_888.me.t.data.di

import a77777_888.me.t.data.local.FavoritesDao
import a77777_888.me.t.data.local.FavoritesDatabase
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import androidx.room.Room

@Module
@InstallIn(SingletonComponent::class)
internal class RoomModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoritesDatabase =
        Room.databaseBuilder(
            context,
            FavoritesDatabase::class.java,
            "favorites_db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFavoritesDao(db: FavoritesDatabase): FavoritesDao = db.getFavouritesDao()
}