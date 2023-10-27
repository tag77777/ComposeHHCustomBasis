package a77777_888.me.t.data.local

import a77777_888.me.t.domain.dataentities.IDbEntity
import a77777_888.me.t.domain.dataentities.IVacanciesListItem
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class VacancyRoomEntity(
    @PrimaryKey override val id: String,
    override val name: String,

    @ColumnInfo(name = "counters_responses") override val countersResponses: Int?,
    @ColumnInfo(name = "counters_total_responses") override val countersTotalResponses: Int?,

    @ColumnInfo(name = "salary_currency") override val salaryCurrency: String?,
    @ColumnInfo(name = "salary_from") override val salaryFrom: Int?,
    @ColumnInfo(name = "salary_to") override val salaryTo: Int?,
    @ColumnInfo(name = "salary_gross") override val salaryGross: Boolean?,

    @ColumnInfo(name = "area_id") override val areaId: String,
    @ColumnInfo(name = "area_name") override val areaName: String,
    @ColumnInfo(name = "area_url") override val areaUrl: String,

    @ColumnInfo(name = "employer_id") override val employerId: String,
    @ColumnInfo(name = "employer_name") override val employerName: String,
    @ColumnInfo(name = "employer_alternate_url") override val employerAlternateUrl: String?,
    @ColumnInfo(name = "employer_logo90") override val employerLogo90: String?,
    @ColumnInfo(name = "employer_logo240") override val employerLogo240: String?,
    @ColumnInfo(name = "employer_logo_original") override val employerLogoOriginal: String?,
    @ColumnInfo(name = "employer_trusted") override val employerTrusted: Boolean,
    @ColumnInfo(name = "employer_url") override val employerUrl: String,
    @ColumnInfo(name = "employer_vacancies_url") override val employerVacanciesUrl: String,

    @ColumnInfo(name = "published_at") override val publishedAt: String
) : IDbEntity {
    constructor(vacancy: IVacanciesListItem): this(
        id = vacancy.id,
        name = vacancy.name,

        countersResponses = vacancy.counters?.responses,
        countersTotalResponses = vacancy.counters?.total_responses,

        areaId = vacancy.area.id,
        areaName = vacancy.area.name,
        areaUrl = vacancy.area.url,

        salaryCurrency = vacancy.salary?.currency,
        salaryFrom = vacancy.salary?.from,
        salaryTo = vacancy.salary?.to,
        salaryGross = vacancy.salary?.gross,

        employerId = vacancy.employer.id,
        employerName = vacancy.employer.name,
        employerAlternateUrl = vacancy.employer.alternateUrl,
        employerLogo90 = vacancy.employer.logoUrls?.logo90,
        employerLogo240 = vacancy.employer.logoUrls?.logo240,
        employerLogoOriginal = vacancy.employer.logoUrls?.original,
        employerTrusted = vacancy.employer.trusted,
        employerUrl = vacancy.employer.url,
        employerVacanciesUrl = vacancy.employer.vacanciesUrl,

        publishedAt = vacancy.publishedAt,
    )
}


