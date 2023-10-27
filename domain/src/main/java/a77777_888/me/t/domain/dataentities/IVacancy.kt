package a77777_888.me.t.domain.dataentities

import a77777_888.me.t.domain.dataentities.vacancy.Area
import a77777_888.me.t.domain.dataentities.vacancy.Employer
import a77777_888.me.t.domain.dataentities.vacancy.Employment
import a77777_888.me.t.domain.dataentities.vacancy.Experience
import a77777_888.me.t.domain.dataentities.vacancy.Salary
import a77777_888.me.t.domain.dataentities.vacancy.Schedule

interface IVacancy {
    val id: String
    val name: String
    val salary: Salary?
    val employer: Employer
    val placedArea: Area
    val publishedAt: String
    val experience: Experience?
    val employment: Employment?
    val schedule: Schedule?
    val acceptTemporary: Boolean?
    val description: String
    val alternateUrl: String
}