package a77777_888.me.t.domain.dataentities.employer

import a77777_888.me.t.domain.dataentities.IEmployer
import com.google.gson.annotations.SerializedName

data class EmployerResponseEntity(
    override val id: String,
    @SerializedName("name") override val name: String, // название компании
    @SerializedName("logo_urls") override val logoUrls: LogoUrls?, // логотипы компании
    @SerializedName("area") override val area: Area, // информация о регионе работодателя
    override val type: String?, // тип компании (прямой работодатель, кадровое агентство и т.п.)
    @SerializedName("site_url") override val siteUrl: String?, // адрес сайта компании
    override val description: String?, // описание компании в виде строки с кодом HTML
    override val industries: List<Industry>, // Cписок отраслей компании.
    @SerializedName("alternate_url") override val alternateUrl: String?, // ссылка на представление компании на сайте
//    val branded_description: String?,
//    val branding: Any,
//    val insider_interviews: List<Interview>, // список интервью или пустой список, если интервью отсутствуют
//    val open_vacancies: Int, // количество открытых вакансий у работодателя
//    val relations: List<Any>, // если работодатель добавлен в черный список, то вернется ['blacklisted'] иначе []
//    val trusted: Boolean, // флаг, показывающий, прошла ли компания проверку на сайте.
//    val vacancies_url: String // ссылка на поисковую выдачу вакансий данной компании
) : IEmployer