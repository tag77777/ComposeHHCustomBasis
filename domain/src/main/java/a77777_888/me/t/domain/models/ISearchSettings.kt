package a77777_888.me.t.domain.models

import a77777_888.me.t.domain.models.searchmodels.FindIn
import a77777_888.me.t.domain.models.searchmodels.Schedule
import kotlinx.coroutines.flow.Flow


// Работа по изменению набора параметров поиска должна начинаться с этого интерфейса

interface ISearchSettings {
   var findWords: String?
   var excludeWords: String?
   var findIn: FindIn
   var regionId: String?
   var experience: String?
   var schedule: Schedule
   var period: String?

   companion object {
      const val FIND_WORDS = "find_words"
      const val EXCLUDE_WORDS = "exclude_words"
      const val FIND_IN_NAME = "name"
      const val FIND_IN_DESCRIPTION = "description"
      const val FIND_IN_COMPANY_NAME = "companyName"
      const val REGION_ID = "region"
      const val EXPERIENCE = "experience"
      const val EXPERIENCE_NO_EXPERIENCE = "noExperience"
      const val EXPERIENCE_1_3 = "between1And3"
      const val EXPERIENCE_3_6 = "between3And6"
      const val EXPERIENCE_MORE_THEN_6 = "moreThan6"
      const val SCHEDULE_REMOTE = "remote"
      const val SCHEDULE_FULL_DAY = "fullDay"
      const val SCHEDULE_SHIFT = "shift"
      const val SCHEDULE_FLEXIBLE = "flexible"
      const val SCHEDULE_FLY_IN_FLY_OUT = "flyInFlyOut"
      const val PERIOD = "period"
   }
}

fun ISearchSettings.copyTo(other: ISearchSettings) {
   other.findWords = this.findWords
   other.excludeWords = this.excludeWords
   other.findIn = this.findIn
   other.regionId = this.regionId
   other.experience = this.experience
   other.schedule = this.schedule
   other.period = this.period
}

interface ISavableSearchSettings : ISearchSettings {
   fun saveSettings()
}

interface IFlowableSearchSettings : ISavableSearchSettings {
   val flow: Flow<ISearchSettings>
   fun emitSettings(settings: ISearchSettings)
}