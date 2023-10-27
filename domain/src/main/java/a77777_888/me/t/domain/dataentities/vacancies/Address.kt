package a77777_888.me.t.domain.dataentities.vacancies

data class Address(
    val building: String,
    val city: String,
    val description: Any,
    val id: String,
    val lat: Double,
    val lng: Double,
    val metro: Any,
    val metro_stations: List<Any>,
    val raw: String,
    val street: String
)