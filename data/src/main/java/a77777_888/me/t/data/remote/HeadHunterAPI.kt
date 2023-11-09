package a77777_888.me.t.data.remote

import a77777_888.me.t.domain.dataentities.areas.Areas
import a77777_888.me.t.domain.dataentities.employer.EmployerResponseEntity
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesResponseEntity
import a77777_888.me.t.domain.dataentities.vacancy.VacancyResponseEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// https://api.hh.ru/openapi/redoc#tag/Soiskatelskie-statusy
// устарело - https://github.com/hhru/api/blob/master/docs/vacancies.md#search
internal interface HeadHunterAPI {

    @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
    // Здесь и далее  необходимо указать название приложения, но поскольку этот проект едва ли
    // когда-нибудь выйдет в продакшн, было принято решение проявить скромность )
    @GET("/areas")
    suspend fun getAreas(): List<Areas>

    @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
    @GET("/areas/{id}")
    suspend fun getArea(@Path("id") id: String): Areas

    @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("responses_count_enabled") responsesCountEnabled: Boolean = true,
        @Query("host") host: String = "hh.ru",
        @Query("page") page: Int = 0,
        @Query("per_page") pageSize: Int = 20,
        @Query("text") find: String? = null,
        @Query("excluded_text") exclude: String? = null,
        @Query("search_field") nameSearchField: String? = "name",
        @Query("search_field") descriptionSearchField: String? = "description",
        @Query("search_field") companyNameSearchField: String? = "company_name",
        @Query("area") regionId: String? = null,
        @Query("experience") experience: String? = null,
        @Query("schedule") remoteSchedule: String? = "remote",
        @Query("schedule") fullDaySchedule: String? = "fullDay",
        @Query("schedule") shiftSchedule: String? = "shift",
        @Query("schedule") flexibleSchedule: String? = "flexible",
        @Query("schedule") flyInFlyOutSchedule: String? = "flyInFlyOut",
        @Query("period") period: String? = null
    ): VacanciesResponseEntity

    @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
    @GET("/vacancies/{id}")
    suspend fun getVacancy(@Path("id") id: String): VacancyResponseEntity

    @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
    @GET("/employers/{id}")
    suspend fun getEmployer(@Path("id") id: String): EmployerResponseEntity

    companion object {
        const val BASE_URL = "https://api.hh.ru"
    }
}


