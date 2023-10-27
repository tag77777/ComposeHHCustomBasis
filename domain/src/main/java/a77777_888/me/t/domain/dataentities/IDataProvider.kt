package a77777_888.me.t.domain.dataentities

import a77777_888.me.t.domain.dataentities.areas.Areas
import a77777_888.me.t.domain.dataentities.employer.EmployerResponseEntity
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesListItem
import a77777_888.me.t.domain.dataentities.vacancy.VacancyResponseEntity
import a77777_888.me.t.domain.models.IFlowableSearchSettings
import androidx.paging.Pager
import kotlinx.coroutines.flow.Flow

interface IDataProvider {
    suspend fun getAreas(): List<Areas>
    fun getVacanciesListPagerFlow(settings: IFlowableSearchSettings): Flow<Pager<Int, VacanciesListItem>> // Flow<PagingData<VacancyItem>>
    suspend fun getVacancy(id: String): VacancyResponseEntity
    suspend fun getEmployer(id: String): EmployerResponseEntity
}

//interface IDataProvider {
//    suspend fun getAreas(): LoadingResult<List<Areas>>
//    suspend fun getPagedVacancies(settings: ISearchSettings): Flow<PagingData<VacancyItem>>
//    suspend fun getVacancy(id: String): LoadingResult<VacancyResponseEntity>
//    suspend fun getEmployer(id: String): LoadingResult<EmployerResponseEntity>
//}
//
