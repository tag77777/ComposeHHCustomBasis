package a77777_888.me.t.domain.dataentities.vacancies

data class VacanciesResponseEntity(
    val alternate_url: String,
    val arguments: Any,
    val clusters: Any,
    val found: Int,
    val items: List<VacanciesListItem>,
    val page: Int,
    val pages: Int,
    val per_page: Int
)