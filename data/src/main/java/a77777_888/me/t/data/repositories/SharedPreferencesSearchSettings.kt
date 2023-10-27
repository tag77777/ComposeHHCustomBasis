package a77777_888.me.t.data.repositories


import a77777_888.me.t.domain.models.IFlowableSearchSettings
import a77777_888.me.t.domain.models.ISearchSettings
import a77777_888.me.t.domain.models.ISearchSettings.Companion.EXCLUDE_WORDS
import a77777_888.me.t.domain.models.ISearchSettings.Companion.EXPERIENCE
import a77777_888.me.t.domain.models.ISearchSettings.Companion.FIND_IN_COMPANY_NAME
import a77777_888.me.t.domain.models.ISearchSettings.Companion.FIND_IN_DESCRIPTION
import a77777_888.me.t.domain.models.ISearchSettings.Companion.FIND_IN_NAME
import a77777_888.me.t.domain.models.ISearchSettings.Companion.FIND_WORDS
import a77777_888.me.t.domain.models.ISearchSettings.Companion.PERIOD
import a77777_888.me.t.domain.models.ISearchSettings.Companion.REGION_ID
import a77777_888.me.t.domain.models.ISearchSettings.Companion.SCHEDULE_FLEXIBLE
import a77777_888.me.t.domain.models.ISearchSettings.Companion.SCHEDULE_FLY_IN_FLY_OUT
import a77777_888.me.t.domain.models.ISearchSettings.Companion.SCHEDULE_FULL_DAY
import a77777_888.me.t.domain.models.ISearchSettings.Companion.SCHEDULE_REMOTE
import a77777_888.me.t.domain.models.ISearchSettings.Companion.SCHEDULE_SHIFT
import a77777_888.me.t.domain.models.searchmodels.FindIn
import a77777_888.me.t.domain.models.searchmodels.Schedule
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesSearchSettings @Inject constructor(
    @ApplicationContext context: Context
) : IFlowableSearchSettings {

        private val sharedPreferences = context.getSharedPreferences(SEARCH_SETTINGS, Context.MODE_PRIVATE)
        private val editor = sharedPreferences.edit()

    override var findWords: String? = sharedPreferences.getString(FIND_WORDS, null)
        set(value) {
            if (field != value ) {
                editor.putString(FIND_WORDS, value)
                field = value
            }
        }

    override var excludeWords: String? =
        sharedPreferences.getString(EXCLUDE_WORDS, null)
        set(value) {
             if (field != value) {
                editor.putString(EXCLUDE_WORDS, value)
                field = value
            }
        }

    override var findIn: FindIn = FindIn(
        name = sharedPreferences.getBoolean(FIND_IN_NAME, false),
        description = sharedPreferences.getBoolean(FIND_IN_DESCRIPTION,false),
        companyName = sharedPreferences.getBoolean(FIND_IN_COMPANY_NAME,false)
    )
        set(value) {
            if (field != value) {
                editor.putBoolean(FIND_IN_NAME, value.name)
                editor.putBoolean(FIND_IN_DESCRIPTION, value.description)
                editor.putBoolean(FIND_IN_COMPANY_NAME, value.companyName)
                field = value
            }
        }

    override var regionId: String? = sharedPreferences.getString(REGION_ID, null)
        set(value) {
            if (field != value) {
                editor.putString(REGION_ID, value)
                field = value
            }
        }

    override var experience: String? = sharedPreferences.getString(EXPERIENCE, null)
        set(value) {
            if (field != value) {
                editor.putString(EXPERIENCE, value)
                field = value
            }
        }

    override var schedule: Schedule = Schedule(
        remote = sharedPreferences.getBoolean(SCHEDULE_REMOTE, false),
        fullDay = sharedPreferences.getBoolean(SCHEDULE_FULL_DAY, false),
        shift = sharedPreferences.getBoolean(SCHEDULE_SHIFT, false),
        flexible = sharedPreferences.getBoolean(SCHEDULE_FLEXIBLE, false),
        flyInFlyOut = sharedPreferences.getBoolean(SCHEDULE_FLY_IN_FLY_OUT, false)
    )
        set(value) {
            if (field != value ) {
                editor.putBoolean(SCHEDULE_REMOTE, value.remote)
                editor.putBoolean(SCHEDULE_FULL_DAY, value.fullDay)
                editor.putBoolean(SCHEDULE_SHIFT, value.shift)
                editor.putBoolean(SCHEDULE_FLEXIBLE, value.flexible)
                editor.putBoolean(SCHEDULE_FLY_IN_FLY_OUT, value.flyInFlyOut)
                field = value
            }
        }

    override var period: String? = sharedPreferences.getString(PERIOD, null)
        set(value) {
            if (field != value) {
                editor.putString(PERIOD, value)
            }
            field = value
        }

    override fun saveSettings() {
        editor.commit()
        Log.e("TAG", "SAVE_SETTINGS")
    }

    override fun emitSettings(settings: ISearchSettings) {
        _flow.value = settings
    }

    private val _flow: MutableStateFlow<ISearchSettings> = MutableStateFlow(this)
    override val flow: Flow<ISearchSettings> = _flow.asStateFlow()

    companion object {
        private const val SEARCH_SETTINGS = "search_settings"
    }
}