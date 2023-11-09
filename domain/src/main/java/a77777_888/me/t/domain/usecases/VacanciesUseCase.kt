package a77777_888.me.t.domain.usecases

import a77777_888.me.t.domain.dataentities.IDataProvider
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesListItem
import a77777_888.me.t.domain.models.IFlowableSearchSettings
import a77777_888.me.t.domain.models.ISearchSettings
import androidx.paging.Pager
import kotlinx.coroutines.flow.Flow

class VacanciesUseCase {
    companion object {
        fun getVacanciesPagerFlow(dataProvider: IDataProvider, settings: IFlowableSearchSettings): Flow<Pager<Int, VacanciesListItem>> =
            dataProvider.getVacanciesListPagerFlow(settings)
    }
}