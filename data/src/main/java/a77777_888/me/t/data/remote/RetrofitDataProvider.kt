package a77777_888.me.t.data.remote

import a77777_888.me.t.data.R
import a77777_888.me.t.domain.dataentities.IDataProvider
import a77777_888.me.t.domain.dataentities.areas.Areas
import a77777_888.me.t.domain.dataentities.employer.EmployerResponseEntity
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesListItem
import a77777_888.me.t.domain.dataentities.vacancy.VacancyResponseEntity
import a77777_888.me.t.domain.models.BackendException
import a77777_888.me.t.domain.models.ConnectionException
import a77777_888.me.t.domain.models.IFlowableSearchSettings
import a77777_888.me.t.domain.models.ISearchSettings
import a77777_888.me.t.domain.models.ParseBackendResponseException
import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitDataProvider @Inject constructor(): IDataProvider {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    override suspend fun getAreas(): List<Areas> =
            wrapExceptions{
                val str = context.getString(R.string.str)
                Log.e("Tag", "str: $str")
                sourceAPI.getAreas()
            }

    override fun getVacanciesListPagerFlow(settings: IFlowableSearchSettings)
            : Flow<Pager<Int, VacanciesListItem>>  {

        val loader: VacancyPageLoader = { pageIndex, pageSize ->
            getVacancies(settings, pageIndex, pageSize)
        }

        return settings.flow.map {
            Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { VacanciesPagingSource(loader, PAGE_SIZE) }
            )
        }


    }

    override suspend fun getVacancy(id: String): VacancyResponseEntity =
        wrapExceptions{ sourceAPI.getVacancy(id) }

    override suspend fun getEmployer(id: String): EmployerResponseEntity = wrapExceptions{
        sourceAPI.getEmployer(id)
    }

    private suspend fun getVacancies(settings: ISearchSettings, pageIndex: Int = 0, pageSize: Int = 20)
            : List<VacanciesListItem> = wrapExceptions{

        val localSettings = object : ISearchSettings{
            override var findWords = settings.findWords
            override var excludeWords = settings.excludeWords
            override var findIn = settings.findIn
            override var regionId = settings.regionId
            override var experience = settings.experience
            override var schedule = settings.schedule
            override var period = settings.period
        }

        with(localSettings){
            val findWordsString = findWords
            val excludeWordsString = excludeWords
            val nameSearchFieldString = if (findIn.name) "name" else null
            val descriptionSearchFieldString = if (findIn.description) "description" else null
            val companyNameSearchFieldString = if (findIn.companyName) "company_name" else null
            val regionIdString = regionId
            val experienceString = experience
            val remoteScheduleString = if (schedule.remote) "remote" else null
            val fullDayScheduleString = if (schedule.fullDay) "fullDay" else null
            val shiftScheduleString = if (schedule.shift) "shift" else null
            val flexibleScheduleString = if (schedule.flexible) "flexible" else null
            val flyInFlyOutScheduleString = if (schedule.flyInFlyOut) "flyInFlyOut" else null
            val periodString = period

            val response = sourceAPI.getVacancies(
                find = findWordsString,
                exclude = excludeWordsString,
                nameSearchField = nameSearchFieldString,
                descriptionSearchField = descriptionSearchFieldString,
                companyNameSearchField = companyNameSearchFieldString,
                regionId = regionIdString,
                experience = experienceString,
                remoteSchedule = remoteScheduleString,
                fullDaySchedule = fullDayScheduleString,
                shiftSchedule = shiftScheduleString,
                flexibleSchedule = flexibleScheduleString,
                flyInFlyOutSchedule = flyInFlyOutScheduleString,
                period = periodString,
                page = pageIndex,
                pageSize = pageSize
            )

            response.items

        }

    }

    private val retrofit = HhRetrofitSource().retrofit

    private val sourceAPI: HeadHunterAPI = //retrofitSource.
            retrofit.create(HeadHunterAPI::class.java)

    private suspend fun <T> wrapExceptions(block: suspend () -> T): T {
        // Выполняет блок и бросает исключения:
        // - BackendException с кодом и сообщением, если сервер вернул ошибку
        // - ParseBackendResponseException, если ответ сервера не может быть обработан
        // - ConnectionException, если запрос не выполнен
        return try {
            block()
        } catch (e: JsonSyntaxException) {
            throw ParseBackendResponseException(text = context.getString(R.string.parsing_error), cause = e)
        } catch (e: HttpException) {
            throw BackendException(
                text = context.getString(R.string.server_error),
                code = e.code(),
                message = e.message())
        } catch (e: IOException){
            throw ConnectionException(text = context.getString(R.string.conection_error), cause =  e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }

}
