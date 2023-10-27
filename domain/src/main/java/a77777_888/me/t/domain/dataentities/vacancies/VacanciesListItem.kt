package a77777_888.me.t.domain.dataentities.vacancies

import a77777_888.me.t.domain.dataentities.IDbEntity
import a77777_888.me.t.domain.dataentities.IVacanciesListItem
import a77777_888.me.t.domain.dataentities.IVacancy
import com.google.gson.annotations.SerializedName

data class VacanciesListItem(
    override val id: String,
    override val name: String,
    override val counters: Counters?,
    override val salary: Salary?,
    override val area: Area,
    override val employer: Employer,
    @SerializedName("published_at") override val publishedAt: String,
//    val accept_temporary: Boolean?,
//    val address: Address?,
//    val adv_response_url: String,
//    val alternate_url: String, // Ссылка на представление вакансии на сайте
//    val apply_alternate_url: String,
//    val archived: Boolean,
//    val contacts: Any?,
//    val created_at: String,
//    val department: Any?,
//    val has_test: Boolean,
//    val insider_interview: Any?,
//    val premium: Boolean,
//    val relations: List<Any>,
//    val response_letter_required: Boolean,
//    val response_url: String?,
//    val schedule: Schedule,
//    val snippet: Snippet,
//    val sort_point_distance: Float?,
//    val type: Type,
//    val url: String,
//    val working_days: List<Any>,
//    val working_time_intervals: List<Any>,
//    val working_time_modes: List<Any>
) : IVacanciesListItem {
    constructor(iVacanciesListItem: IVacanciesListItem): this(
        id  = iVacanciesListItem.id,
        name  = iVacanciesListItem.name,
        counters  = iVacanciesListItem.counters,
        salary = iVacanciesListItem.salary,
        area= iVacanciesListItem.area,
        employer = iVacanciesListItem.employer,
        publishedAt = iVacanciesListItem.publishedAt
    )
    constructor(iDbEntity: IDbEntity): this(
        id = iDbEntity.id,
        name = iDbEntity.name,
        counters = if (iDbEntity.countersResponses == null) null
                   else Counters(
                            responses = iDbEntity.countersResponses!!,
                            total_responses = iDbEntity.countersTotalResponses!!
                        ),
        salary = if (iDbEntity.salaryCurrency == null) null
                 else Salary(
                            currency = iDbEntity.salaryCurrency!!,
                            from = iDbEntity.salaryFrom,
                            to = iDbEntity.salaryTo,
                            gross = iDbEntity.salaryGross!!
                      ),
        area = Area(
                    id = iDbEntity.areaId,
                    name = iDbEntity.areaName,
                    url =  iDbEntity.areaUrl
               ),
        employer = Employer(
                        id = iDbEntity.employerId,
                        name = iDbEntity.employerName,
                        alternateUrl = iDbEntity.employerAlternateUrl,
                        logoUrls = if (iDbEntity.employerLogo90 == null) null
                                   else LogoUrls(
                                            logo90 = iDbEntity.employerLogo90!!,
                                            logo240 = iDbEntity.employerLogo240!!,
                                            original = iDbEntity.employerLogoOriginal!!
                                        ),
                        trusted = iDbEntity.employerTrusted,
                        url = iDbEntity.employerUrl,
                        vacanciesUrl = iDbEntity.employerVacanciesUrl
                   ),
        publishedAt = iDbEntity.publishedAt
    )
    constructor(iVacancy: IVacancy): this(
        id  = iVacancy.id,
        name  = iVacancy.name,
        counters  = null,
        salary = if (iVacancy.salary == null) null else Salary(
            currency = iVacancy.salary!!.currency,
            from = iVacancy.salary!!.from,
            gross = iVacancy.salary!!.gross,
            to = iVacancy.salary!!.to
        ),
        area= Area(
            id = iVacancy.placedArea.id,
            name = iVacancy.placedArea.name,
            url = iVacancy.placedArea.url
        ),
        employer = Employer(
            alternateUrl = iVacancy.employer.alternateUrl,
            id = iVacancy.employer.id,
            logoUrls = if (iVacancy.employer.logoUrls == null) null else LogoUrls(
                logo240 = iVacancy.employer.logoUrls!!.logo240,
                logo90 = iVacancy.employer.logoUrls!!.logo90,
                original = iVacancy.employer.logoUrls!!.original
            ),
            name = iVacancy.employer.name,
            trusted = iVacancy.employer.trusted,
            url = iVacancy.employer.url,
            vacanciesUrl= iVacancy.employer.vacanciesUrl,
        ),
        publishedAt = iVacancy.publishedAt
    )
}