package a77777_888.me.t.composehhcustombasis.ui.navigation

sealed class Destination(val route: String) {
    data object SearchList: Destination(route = "search_list")
    data object Favorites: Destination(route = "favorites_list")
    data object DetailsGraph: Destination(route = "details_graph")
    data object VacancyDetails: Destination(route = "vacancy_details")
    class  EmployerDetails(id: String): Destination(route = "employer_details/$id")

}


