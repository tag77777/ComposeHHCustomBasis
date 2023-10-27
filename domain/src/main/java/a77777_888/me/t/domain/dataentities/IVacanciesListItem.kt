package a77777_888.me.t.domain.dataentities

import a77777_888.me.t.domain.dataentities.vacancies.Area
import a77777_888.me.t.domain.dataentities.vacancies.Counters
import a77777_888.me.t.domain.dataentities.vacancies.Employer
import a77777_888.me.t.domain.dataentities.vacancies.Salary

interface IVacanciesListItem {
    val id: String
    val name: String
    val counters: Counters?
    val salary: Salary?
    val area: Area
    val employer: Employer
    val publishedAt: String
}

interface IDbEntity {
    val id: String
    val name: String

    val countersResponses: Int?
    val countersTotalResponses: Int?

    val areaId: String
    val areaName: String
    val areaUrl: String

    val salaryCurrency: String?
    val salaryFrom: Int?
    val salaryTo: Int?
    val salaryGross: Boolean?

    val employerId: String
    val employerName: String
    val employerAlternateUrl: String?
    val employerLogo90: String?
    val employerLogo240: String?
    val employerLogoOriginal: String?
    val employerTrusted: Boolean
    val employerUrl: String
    val employerVacanciesUrl: String

    val publishedAt: String
}