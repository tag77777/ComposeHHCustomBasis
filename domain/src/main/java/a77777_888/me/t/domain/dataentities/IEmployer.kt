package a77777_888.me.t.domain.dataentities

import a77777_888.me.t.domain.dataentities.employer.Area
import a77777_888.me.t.domain.dataentities.employer.Industry
import a77777_888.me.t.domain.dataentities.employer.LogoUrls

interface IEmployer {
    val id: String
    val name: String
    val logoUrls: LogoUrls?
    val area: Area?
    val type: String?
    val siteUrl: String?
    val description: String?
    val industries: List<Industry>
    val alternateUrl: String?
}