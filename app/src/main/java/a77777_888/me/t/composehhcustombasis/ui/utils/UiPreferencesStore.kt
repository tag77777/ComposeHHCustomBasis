package a77777_888.me.t.composehhcustombasis.ui.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val THEME_PREFERENCES = "theme_preferences"

@Singleton
class UiPreferencesStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = THEME_PREFERENCES)

    val userPreferencesFlow: Flow<Boolean> = context.dataStore.data.map{ preferences ->
        preferences[PreferencesKeys.IS_DARK_THEME] ?: false
    }

    suspend fun updateThemeChanges(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDarkTheme
        }
    }

    private object PreferencesKeys {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

}