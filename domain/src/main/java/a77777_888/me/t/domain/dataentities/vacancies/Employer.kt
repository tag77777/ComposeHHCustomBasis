package a77777_888.me.t.domain.dataentities.vacancies

import com.google.gson.annotations.SerializedName

data class Employer(
    @SerializedName("alternate_url") val alternateUrl: String?,
    val id: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
    val url: String,
    @SerializedName("vacancies_url") val vacanciesUrl: String
)